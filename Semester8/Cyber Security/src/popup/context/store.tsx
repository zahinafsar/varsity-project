import { createContext, useContext, useEffect, useState } from "react";
import { routes } from "../../router";
import { nanoid } from "nanoid";

export type Password = {
  id: string;
  url: string;
  password: string;
};

interface StoreContext {
  route: string;
  navigate: (update: string | ((r: typeof routes) => string)) => void;
  masterKey?: string;
  setMasterKey?: (key: string) => void;
  passwords?: Password[];
  addPassword?: (p: Omit<Password, "id">) => void;
  removePassword?: (id: string) => void;
}

const Store = createContext<StoreContext>({
  route: "",
  navigate: () => {},
  masterKey: "",
  setMasterKey: () => {},
});

export const StoreProvider = ({ children }) => {
  const [route, setRoute] = useState(routes.lock);
  const [masterKey, setMasterKey] = useState("");
  const [passwords, setPasswords] = useState<Password[]>([]);

  useEffect(() => {
    const passwordsString = localStorage.getItem("passwords");
    if (passwordsString) {
      const passwords = JSON.parse(passwordsString);
      setPasswords(passwords);
    }
  }, []);

  const navigate = (update: string | ((r: typeof routes) => string)) => {
    if (typeof update === "function") {
      setRoute(update(routes));
    } else {
      setRoute(update);
    }
  };

  const addPassword = (p: Omit<Password, "id">) => {
    const password = { ...p, id: nanoid() };
    const passwordArray = [...passwords, password];
    setPasswords(passwordArray);
    const passwordsString = JSON.stringify(passwordArray);
    localStorage.setItem("passwords", passwordsString);
  };

  const removePassword = (id: string) => {
    const passwordArray = passwords.filter((p) => p.id !== id);
    setPasswords(passwordArray);
    const passwordsString = JSON.stringify(passwordArray);
    localStorage.setItem("passwords", passwordsString);
  };

  return (
    <Store.Provider
      value={{
        route,
        navigate,
        masterKey,
        setMasterKey,
        passwords,
        addPassword,
        removePassword,
      }}
    >
      {children}
    </Store.Provider>
  );
};

export const useStore = () => useContext(Store);
