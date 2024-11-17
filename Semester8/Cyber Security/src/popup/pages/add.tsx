import { useState } from "react";
import Input from "../components/input";
import Layout from "../components/layout";
import { useStore } from "../context/store";

const url = window.location.href;
function Add() {
  const { navigate, addPassword } = useStore();
  const [password, setPassword] = useState({
    password: "",
    url,
  });

  const handlePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword({
      ...password,
      [e.target.name]: e.target.value,
    });
  };

  const home = () => navigate((r) => r.home);
  const save = () => {
    addPassword({
      password: password.password,
      url: password.url,
    });
    home();
  };

  return (
    <Layout>
      <div className="flex flex-col justify-center items-center">
        <div className="w-full border-b border-gray-500 px-3 py-2 flex items-center justify-between">
          <h1 className="text-xl font-bold text-center mb-1">SPM</h1>
          <div className="flex items-center gap-3">
            <p onClick={home} className="text-xs text-blue-500 cursor-pointer">
              Home
            </p>
            <p onClick={save} className="text-xs text-green-500 cursor-pointer">
              Save
            </p>
          </div>
        </div>
        <div className="p-3 w-full flex flex-col gap-2">
          <Input
            onChange={handlePassword}
            defaultValue={url}
            name="url"
            label="Url"
          />
          <Input
            onChange={handlePassword}
            name="password"
            label="Password"
            type="password"
          />
        </div>
      </div>
    </Layout>
  );
}

export default Add;
