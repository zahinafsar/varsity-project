import ReactDOM from "react-dom";
import Modal from "./modal";
import "./local.css";

export const rootElement = document.createElement("div");
rootElement.id = "spm";
document.body.appendChild(rootElement);
ReactDOM.render(<Modal />, document.getElementById("spm"));
