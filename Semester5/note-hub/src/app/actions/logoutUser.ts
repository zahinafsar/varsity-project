"use server";

import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export default async function logoutUser(name: string) {
  cookies().delete(name);
  redirect("/login");
}
