import { User } from "@/types";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export const getUser = () => {
    const cookieStore = cookies();
    const user = cookieStore.get("user");
    if (!user) return redirect("/login");
    return JSON.parse(user?.value as string) as User;
}