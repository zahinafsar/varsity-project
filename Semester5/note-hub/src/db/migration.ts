import fs from "fs";
import { connectDb } from "../db/db";

export async function initDb() {
  const connection = connectDb();

  const sqlFiles = loadFilesByExtension("./src/db/schema", ".sql");

  // read sql files and execute
  for (const file of sqlFiles) {
    const sql = fs.readFileSync(`./src/db/schema/${file}`, "utf8");
    connection.query(sql, (err, result) => {
      if (err) throw err;
      console.log(result);
    });
  }

  // const d = connection.query("SELECT 1 + 1 AS solution");

  // console.log("The solution is: ", d);
}

const loadFilesByExtension = (dir: string, extension: string) => {
  const files = fs.readdirSync(dir);

  return files.filter((file) => file.endsWith(extension));
};
