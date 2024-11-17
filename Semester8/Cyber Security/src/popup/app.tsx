import ReactDOM from "react-dom";
import { StoreProvider } from "./context/store";
import Router from "./router";

ReactDOM.render(
  <StoreProvider>
    <Router />
  </StoreProvider>,
  document.getElementById("spm-popup")
);
