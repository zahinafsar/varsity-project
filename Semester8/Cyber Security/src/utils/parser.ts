import { IQuery } from "../interface/message";

export function generateQuery({ query }: { query: IQuery[] }) {
  let q = "";
  query.slice(-3).forEach((item) => {
    if (item.question) {
      q += "Question: " + item.question + "\n";
    } else if (item.answer) {
      q += "Answer: " + item.answer + "\n";
    }
  });
  q += "Answer: ";
  return q;
}
