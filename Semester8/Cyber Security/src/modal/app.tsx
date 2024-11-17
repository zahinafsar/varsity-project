import ReactDOM from "react-dom";
import Modal from "./modal";

const isExist = document.getElementById("spm");
export const rootElement = document.createElement("div");

if (!isExist) {
  rootElement.id = "spm";
  document.body.appendChild(rootElement);
  ReactDOM.render(<Modal />, document.getElementById("spm"));
}
