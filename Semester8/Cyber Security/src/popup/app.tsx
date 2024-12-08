import ReactDOM from "react-dom";
import Layout from "../components/layout";

ReactDOM.render(
  <Layout>
    <div className="flex flex-col justify-center items-center">
      <div className="w-full border-b border-gray-500 px-3 py-2 flex items-center justify-between">
        <h1 className="text-xl font-bold text-center mb-1">Welcome to Secure Password Manager</h1>
      </div>
    </div>
  </Layout>,
  document.getElementById("spm-popup")
);
