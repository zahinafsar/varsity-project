require("dotenv").config();

const puppeteer = require("puppeteer");
const axios = require("axios");
const { sendEmail } = require("./email");
const { format } = require("date-fns");

const sections = [
  {
    courseName: "Compiler Lab",
    courseId: 457,
    sectionCode: "222_D5",
  },
];

const main = async () => {
  console.log("Running on", format(new Date(), "hh:mm a"));
  const browser = await puppeteer.launch({
    headless: "new",
    executablePath: "/usr/bin/chromium-browser",
    args: ["--no-sandbox"],
  });
  const page = await browser.newPage();
  const timeout = 20000;
  page.setDefaultTimeout(timeout);

  try {
    await page.goto("https://studentportal.green.edu.bd/");
    await page.waitForSelector(`#Input_LoginId`, { timeout, visible: true });

    await page.type(`#Input_LoginId`, "221902014");
    await page.type(`#Input_Password`, process.env.PASSWORD);

    await Promise.all([page.click(`button`), page.waitForNavigation()]);

    const cookies = await page.cookies();

    const headers = {
      accept: "application/json, text/plain, */*",
      "accept-language": "en-US,en;q=0.9,bn;q=0.8",
      "sec-ch-ua":
        '"Google Chrome";v="119", "Chromium";v="119", "Not?A_Brand";v="24"',
      "sec-ch-ua-mobile": "?0",
      "sec-ch-ua-platform": '"macOS"',
      "sec-fetch-dest": "empty",
      "sec-fetch-mode": "cors",
      "sec-fetch-site": "same-origin",
      cookie: cookies.map((c) => `${c.name}=${c.value}`).join("; "),
      Referer:
        "https://studentportal.green.edu.bd/Student/StudentSectionSelection",
      "Referrer-Policy": "strict-origin-when-cross-origin",
    };

    const allCourse = await getCourses(headers);

    sections.forEach(async (section) => {
      const { data } = await axios.post(
        `https://studentportal.green.edu.bd/api/CourseSectionInfo?acaCalId=71&programId=2&courseId=${section.courseId}&versionId=1`,
        null,
        {
          headers,
        }
      );
      const open = data.find((d) => d.sectionName === section.sectionCode);
      const course = allCourse.find((c) => c.courseID === section.courseId);
      if (course.sectionName === null) {
        if (open?.capacity > open?.occupied) {
          console.log("Seat available for", section.courseName);
          selectSection(headers, {
            sheetId: course.regWorksheetId,
            sectionId: open.acaCal_SectionID,
            sectionName: section.sectionCode,
            courseCode: open.formalCode,
            courseId: section.courseId,
          });
          console.log("Section selected for", section.courseName);
          sendEmail({
            subject: section.courseName,
            availableSeat: open.capacity - open.occupied,
          });
        } else {
          console.log("No seat available for", section.courseName);
        }
      } else {
        console.log("You already got a section for", section.courseName);
      }
    });
  } catch (err) {
    console.log("Failed to run the script");
  } finally {
    await browser.close();
  }
};

const selectSection = async (
  headers,
  { sectionId, sectionName, courseCode, courseId, sheetId }
) => {
  const url = "https://studentportal.green.edu.bd/api/SectionTake";
  const params = new URLSearchParams();
  params.append("regWorkSheetId", sheetId);
  params.append("newSectionId", sectionId);
  params.append("sectionName", sectionName);
  params.append("studentId", "24134");
  params.append("courseCode", courseCode);
  params.append("courseId", courseId);
  params.append("versionId", "1");
  params.append("programId", "2");
  params.append(
    "url",
    "https://studentportal.green.edu.bd/Student/StudentSectionSelection"
  );

  const urlWithParams = url + "?" + params.toString();

  await axios.post(urlWithParams, null, {
    headers,
  });
};

const getCourses = async (headers) => {
  const { data } = await axios.post(
    "https://studentportal.green.edu.bd/api/AutoAssignCourse?studentId=24134",
    null,
    {
      headers,
      referrer:
        "https://studentportal.green.edu.bd/Student/StudentSectionSelection",
      referrerPolicy: "strict-origin-when-cross-origin",
      body: null,
      method: "POST",
      mode: "cors",
      credentials: "include",
    }
  );
  return data;
};

main();
