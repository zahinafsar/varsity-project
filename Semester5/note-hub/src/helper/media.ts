import path from "path";
import { writeFile } from "fs/promises";
import { existsSync, mkdirSync } from "fs";

export const uploadFile = async (
  file: File,
  filename: FormDataEntryValue | null
) => {
  const buffer = Buffer.from(await file.arrayBuffer());
  const fileName = `${filename || Date.now()}.png`;
  try {
    if (!existsSync("public/uploads")) {
      mkdirSync("public/uploads", { recursive: true });
    }
    await writeFile(
      path.join(process.cwd(), "public/uploads/" + fileName),
      buffer
    );
    return process.env.BASE_URL + "/uploads/" + fileName;
  } catch (error) {
    console.log("Error occured ", error);
    throw error;
  }
};