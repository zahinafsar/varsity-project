"use server";

import { executeQuery } from "@/db/query";
import { uploadFile } from "@/helper/media";
import { cookies } from "next/headers";
import { v4 as uid } from "uuid";

export async function createNote(data: FormData) {
  const cookieStore = cookies();
  const user = cookieStore.get("user");
  const userData = JSON.parse(user?.value || "{}");

  const title = data.get("title");
  const content = data.get("content");
  const images = data.getAll("images");

  console.log(title, content, images);

  const imagesUrls = await Promise.all(
    Array.from(images || []).map(async (image, index) => {
      return await uploadFile(image as File, uid());
    })
  );

  try {
    if (!userData.id) {
      throw "User not found";
    }
    const data = [userData.id, title, content, JSON.stringify(imagesUrls)];
    await executeQuery("create_note", data);
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
}
