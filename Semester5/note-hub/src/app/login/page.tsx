import { hashPassword } from "@/helper/algo";
import Link from "next/link";
import { redirect } from "next/navigation";
import React from "react";
import { cookies } from "next/headers";
import { SubmitButton } from "@/components/submitButton";
import { executeQuery } from "@/db/query";
import { User } from "@/types";

type Props = {
  searchParams: { error: string };
};

export default function Login({ searchParams }: Props) {
  const error = searchParams.error;

  async function handleSignin(formData: FormData) {
    "use server";

    const rawFormData = {
      student_id: formData.get("student_id"),
      password: formData.get("password"),
    };

    try {
      const res = await executeQuery<User[][]>("login_user", [
        rawFormData.student_id,
      ]);
      const user = res?.at(0);

      if (!user) {
        return redirect("/login?error=User Not Found!");
      }

      const passedPassword = String(formData.get("password"));

      const dbHashedPassword = +user.password;
      const hashedPassword = hashPassword(passedPassword);

      if (dbHashedPassword == hashedPassword) {
        console.log("Login successfull!");
        const cookieStore = cookies();
        cookieStore.set("user", JSON.stringify(user));
        return redirect("/");
      } else {
        console.log("Incorrect password!", {
          dbHashedPassword,
          passedPassword,
          hashedPassword,
        });
      }
    } catch (err) {
      if (err) throw err;
      console.log("Error in login", err);
    }
  }

  return (
    <div className="bg-gray-100 flex justify-center items-center h-screen">
      <div className="w-1/2 h-screen bg-slate-700 flex justify-center items-center">
        <p className="text-6xl font-extrabold">
          Note<span className="text-amber-400">Hub</span>
        </p>
      </div>
      <div className="lg:p-36 md:p-52 sm:20 p-8 w-full lg:w-1/2">
        <h1 className="text-2xl font-semibold mb-4">Login</h1>
        <form action={handleSignin}>
          <div className="mb-4">
            <label htmlFor="student_id" className="block text-gray-600">
              Student ID
            </label>
            <input
              type="text"
              id="student_id"
              name="student_id"
              className="w-full border rounded-md py-2 px-3 bg-slate-200 text-black"
              autoComplete="off"
            />
          </div>
          <div className="mb-4">
            <label htmlFor="password" className="block text-gray-600">
              Password
            </label>
            <input
              type="password"
              id="password"
              name="password"
              className="w-full border rounded-md py-2 px-3 bg-slate-200 text-black"
              autoComplete="off"
            />
          </div>
          <div className="mt-4">
            <p className="text-center text-red-500 mb-2">{error}</p>
            <SubmitButton text="Login" />
          </div>
        </form>
        <div className="mt-6 text-blue-500 text-center">
          <Link href="/signup" className="hover:underline">
            Sign up Here
          </Link>
        </div>
      </div>
    </div>
  );
}
