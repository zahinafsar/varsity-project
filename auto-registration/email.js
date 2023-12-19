const sendGridMail = require("@sendgrid/mail");
sendGridMail.setApiKey(process.env.SEND_GRID);

exports.sendEmail = async ({ subject, availableSeat }) => {
  try {
    await sendGridMail.send({
      from: {
        name: "Pre-registration bot",
        email: "contact@findinghealers.com",
      },
      to: "afsarzahin@gmail.com",
      subject: `Seat Open for ${subject}!`,
      html: `<strong>We found ${availableSeat} available seat for ${subject}. Hurry up and take it.</strong>`,
    });
    console.log(`Email sent to ${to}...`);
  } catch (error) {
    console.log("SendGrid", "Something went wrong while sending email.");
  }
};
