const puppeteer = require("puppeteer");
const { sendEmail } = require("./email");
const { format } = require("date-fns");

const sections = [
  {
    course: "Web Programming",
    section: "213_D5",
    url: "https://studentportal.green.edu.bd/api/CourseSectionInfo?acaCalId=71&programId=2&courseId=448&versionId=1",
  },
  {
    course: "Compiler Lab",
    section: "221_D5",
    url: "https://studentportal.green.edu.bd/api/CourseSectionInfo?acaCalId=71&programId=2&courseId=457&versionId=1",
  },
];

const main = async () => {
  console.log("Running on", format(new Date(), "hh:mm a"));
  const browser = await puppeteer.launch({ headless: "new" });
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
          const open = data.find((d) => d.sectionName === section.section);
          if (open?.capacity > open?.occupied) {
            console.log("Seat available for", section.course);
            sendEmail({
              subject: section.course,
              availableSeat: open.capacity - open.occupied,
            });
          } else {
            console.log("No seat available for", section.course);
          }
        });
    });
  } catch (err) {
    console.log(err);
  } finally {
    await browser.close();
  }
};

main();
