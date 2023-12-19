const puppeteer = require("puppeteer");
const { sendEmail } = require("./email");
const { format } = require("date-fns");
const fetch = require("node-fetch");

const sections = [
  {
    courseName: "Web Programming",
    courseId: 448,
    sectionCode: "213_D5",
    url: "https://studentportal.green.edu.bd/api/CourseSectionInfo?acaCalId=71&programId=2&courseId=448&versionId=1",
  },
  {
    courseName: "Compiler Lab",
    courseId: 457,
    sectionCode: "221_D5",
    url: "https://studentportal.green.edu.bd/api/CourseSectionInfo?acaCalId=71&programId=2&courseId=457&versionId=1",
  },
  // {
  //   courseName: "Data Communication",
  //   courseId: 458,
  //   sectionCode: "221_D1",
  //   url: "https://studentportal.green.edu.bd/api/CourseSectionInfo?acaCalId=71&programId=2&courseId=458&versionId=1",
  // },
];

const main = async () => {
  console.log("Running on", format(new Date(), "hh:mm a"));
  const browser = await puppeteer.launch({
  	headless: "new",
	executablePath: '/usr/bin/chromium-browser',
	args: ["--no-sandbox"]
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

    sections.forEach((section) => {
      fetch(section.url, {
        headers,
        method: "POST",
      })
        .then((res) => res.json())
        .then((data) => {
          const open = data.find((d) => d.sectionName === section.sectionCode);
          if (open?.capacity > open?.occupied) {
            console.log("Seat available for", section.courseName);
            selectSection(headers, {
              sectionId: open.acaCal_SectionID,
              sectionName: section.sectionCode,
              courseCode: open.formalCode,
              courseId: section.courseId,
            });
            sendEmail({
              subject: section.courseName,
              availableSeat: open.capacity - open.occupied,
            });
          } else {
            console.log("No seat available for", section.courseName);
          }
        });
    });
  } catch (err) {
    console.log(err);
  } finally {
    await browser.close();
  }
};

const selectSection = async (
  headers,
  { sectionId, sectionName, courseCode, courseId }
) => {
  const url = "https://studentportal.green.edu.bd/api/SectionTake";
  const params = new URLSearchParams();
  params.append("regWorkSheetId", "9055798");
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

  fetch(urlWithParams, {
    headers,
    method: "POST",
  }).then(() => {
    console.log("Successfully registered on", sectionName);
  });
};

main();
