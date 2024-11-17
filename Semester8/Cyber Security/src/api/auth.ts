import { api } from ".";

export const _registration = async ({
    username,
    password,
    email
}) => {
  return api.post("/auth/registration", {
    username,
    password,
    email
  });
};

export const _login = async ({
  password,
  email
}) => {
return api.post("/auth/login", {
  password,
  email
});
};
