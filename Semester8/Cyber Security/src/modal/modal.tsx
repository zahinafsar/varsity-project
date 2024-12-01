import Input from "../components/input";
import Draggable from "../components/draggable";
import { useEffect, useState } from "react";
import Button from "../components/button";
import { AES } from "../utils/aes";

type Password = {
  password: string;
  url: string;
};

const Modal = () => {
  const [page, setPage] = useState<"login" | "add" | "view">("login");
  const [masterKey, setMasterKey] = useState("");
  const [password, setPassword] = useState({
    password: "",
    url: window.location.href,
  });
  const [passwords, setPasswords] = useState<Password[]>([]);

  useEffect(() => {
    let tempPasswords = [];
    try {
      console.log(localStorage.getItem("passwords") || "[]");
      tempPasswords = JSON.parse(localStorage.getItem("passwords") || "[]");
      console.log(tempPasswords);
    } catch (error) {}
    setPasswords(tempPasswords);
  }, []);

  const handleClose = () => {
    document.getElementById("spm")?.remove();
  };

  const onKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      setPage("view");
    }
  };

  const handlePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword({
      ...password,
      [e.target.name]: e.target.value,
    });
  };

  const save = () => {
    if (!password.password || !password.url) return;
    const aes = new AES(masterKey);
    const encrypted = aes.encrypt(password.password);
    const tempPasswords = [
      ...passwords,
      { ...password, password: aes.bytesToHex(encrypted) },
    ];
    console.log(tempPasswords);
    localStorage.setItem("passwords", JSON.stringify(tempPasswords));
    setPasswords(tempPasswords);
    setPage("view");
  };

  const copy = (password: Password) => {
    const aes = new AES(masterKey);
    const decrypted = aes.decrypt(password.password);
    navigator.clipboard.writeText(aes.bytesToString(decrypted));
  };

  const remove = (index: number) => {
    const tempPasswords = passwords.filter((_, i) => i !== index);
    localStorage.setItem("passwords", JSON.stringify(tempPasswords));
    setPasswords(tempPasswords);
  };

  return (
    <Draggable
      className={"spm-container"}
      dragger={
        <div className="spm-modal-bar">
          <h3 style={{ margin: 0, color: "white" }}>Secure Password Manager</h3>
          <div className="flex items-center gap-2">
            {page !== "login" &&
              (page === "view" ? (
                <p
                  className="text-yellow-400 cursor-pointer text-sm"
                  onClick={() => setPage("add")}
                >
                  Add
                </p>
              ) : (
                <p
                  className="text-yellow-400 cursor-pointer text-sm"
                  onClick={() => setPage("view")}
                >
                  View
                </p>
              ))}
            <p
              className="text-red-400 cursor-pointer text-sm"
              onClick={handleClose}
            >
              Close
            </p>
          </div>
        </div>
      }
    >
      {page === "login" ? (
        <div className="px-3 pb-3">
          <Input
            label="Master Key"
            name="masterKey"
            type="password"
            autoFocus
            value={masterKey}
            onChange={(e) => setMasterKey(e.target.value)}
            onKeyDown={onKeyPress}
          />
        </div>
      ) : page === "add" ? (
        <div className="px-3 pb-3 w-full flex flex-col gap-2">
          <Input
            onChange={handlePassword}
            defaultValue={password.url}
            name="url"
            label="Url"
          />
          <Input
            onChange={handlePassword}
            name="password"
            label="Password"
            type="password"
          />
          <Button className="mt-4" onClick={save}>
            Save
          </Button>
        </div>
      ) : (
        <div className="divide-y divide-gray-500 w-full max-h-[300px] overflow-auto">
          {passwords.length ? (
            passwords.map((password, index) => {
              return (
                <div
                  key={index}
                  className="flex justify-between items-center px-3 py-3"
                >
                  <div className="w-full">
                    <div className="flex justify-between items-center">
                      <p className="text-sm max-w-[70%] truncate">
                        {password.url}
                      </p>
                      <div className="flex items-center gap-2">
                        <p
                          onClick={() => copy(password)}
                          className="text-sm text-green-400 cursor-pointer"
                        >
                          Copy
                        </p>
                        <p
                          onClick={() => remove(index)}
                          className="text-sm text-blue-400 cursor-pointer"
                        >
                          Remove
                        </p>
                      </div>
                    </div>
                    <Input
                      type="password"
                      value={password.password}
                    />
                  </div>
                </div>
              );
            })
          ) : (
            <div className="py-10 px-4">
              <p className="w-full text-center">No passwords saved</p>
            </div>
          )}
        </div>
      )}
    </Draggable>
  );
};

export default Modal;
