// const sendGridMail = require("@sendgrid/mail");
// sendGridMail.setApiKey(process.env.SEND_GRID);

// exports.sendEmail = async ({ subject, availableSeat }) => {
//   try {
//     await sendGridMail.send({
//       from: {
//         name: "Pre-registration bot",
//         email: "contact@findinghealers.com",
//       },
//       to: "afsarzahin@gmail.com",
//       subject: `Seat Open for ${subject}!`,
//       html: `<strong>We found ${availableSeat} available seat for ${subject}. Hurry up and take it.</strong>`,
//     });
//     console.log(`Email sent to ${to}...`);
//   } catch (error) {
//     console.log("SendGrid", "Something went wrong while sending email.");
//   }
// };


// const sections = [
//   {
//       courseName: "Microprocessors",
//       courseId: 454,
//       sectionCode: "222_D1",
//   },
//   {
//       courseName: "Computer Networking",
//       courseId: 462,
//       sectionCode: "221_D7",
//   },
//   {
//       courseName: "Computer and Cyber Security",
//       courseId: 474,
//       sectionCode: "221_D3",
//   },
//   {
//       courseName: "Computer Networking Lab",
//       courseId: 463,
//       sectionCode: "221_D20",
//   },
// ];
