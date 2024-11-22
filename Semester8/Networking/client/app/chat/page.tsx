import { cookies } from "next/headers";
import { redirect } from "next/navigation";
import { ChatForm } from "./form";
import { LogoutButton } from "./logout";

export default async function Home() {
  const cookieStore = await cookies();
  const username = cookieStore.get("username")?.value;
  if (!username) redirect("/login");

  return (
    <>
      <LogoutButton />
      <ChatForm username={username} />
    </>
  );
}
