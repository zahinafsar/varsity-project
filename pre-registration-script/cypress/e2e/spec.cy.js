const url = "https://studentportal.green.edu.bd";
const id = "221902014";
const password = "zahin123";

describe("empty spec", () => {
  it("visit", () => {
    cy.visit(url);
  });
  it("login", () => {
    const idInput = cy.get("#Input_LoginId");
    const passwordInput = cy.get("#Input_Password");
    const loginButton = cy.get('.btn');

    idInput.type(id);
    passwordInput.type(password);

    loginButton.click();
  });
  it("login", () => {
    cy.wa
  });

});
