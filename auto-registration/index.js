require("dotenv").config();

const puppeteer = require("puppeteer");

const config = {
    acaCalId: 72,
    programId: 2,
    versionId: 1,
    studentId: 24134,
}

const sections = [
    {
        courseCode: "CSE 303",
        sectionCode: "222_D1",
    },
    {
        courseCode: "CSE 311",
        sectionCode: "221_D7",
    },
];

const main = async () => {
    console.log("Running on", new Date().toLocaleTimeString());
    const browser = await puppeteer.launch({
        headless: "new",
        // executablePath: "/usr/bin/chromium-browser",
        args: ["--no-sandbox", '--incognito'],
    });
    const page = await browser.newPage();
    const timeout = 20000;
    page.setDefaultTimeout(timeout);

    try {
        await page.goto("https://studentportal.green.edu.bd/");
        await page.waitForSelector(`#Input_LoginId`, { timeout, visible: true });
        await page.type(`#Input_LoginId`, process.env.STUDENT_ID);
        await page.type(`#Input_Password`, process.env.PASSWORD);

        await Promise.all([page.click(`button`), page.waitForNavigation()]);

        const api = (url, params) => page.evaluate(async (url, params) => {
            const newURL = url + "?" + new URLSearchParams(params).toString();
            return fetch(newURL, {
                "method": "POST",
                "credentials": "include"
            }).then(response => response.json());
        }, url, params);

        const allCourse = await api(
            "https://studentportal.green.edu.bd/api/AutoAssignCourse",
            { studentId: config.studentId }
        );

        sections.forEach(async (section) => {
            const course = allCourse.find((c) => c.formalCode.includes(section.courseCode));

            const data = await api(
                "https://studentportal.green.edu.bd/api/CourseSectionInfo",
                {
                    acaCalId: config.acaCalId,
                    programId: config.programId,
                    courseId: course.courseID,
                    versionId: config.versionId,
                }
            );

            const open = data.find((d) => d.sectionName === section.sectionCode);

            if (course.sectionName === null) {
                if (open?.capacity > open?.occupied) {
                    console.log("Seat available for", course.courseTitle);
                    await api(
                        "https://studentportal.green.edu.bd/api/SectionTake",
                        {
                            regWorkSheetId: course.regWorksheetId,
                            newSectionId: open.acaCal_SectionID,
                            sectionName: section.sectionCode,
                            studentId: config.studentId,
                            courseCode: open.formalCode,
                            courseId: course.courseID,
                            versionId: config.versionId,
                            programId: config.programId,
                            url: "https://studentportal.green.edu.bd/Student/StudentSectionSelection",
                        }
                    );
                    console.log("Section selected for", course.courseTitle);
                } else {
                    console.log("No seat available for", course.courseTitle);
                }
            } else {
                console.log("You already got a section for", course.courseTitle);
            }
        });
    } catch (error) {
        console.log("Error", error);
    }
};

setInterval(() => {
    main();
}, 10000)
