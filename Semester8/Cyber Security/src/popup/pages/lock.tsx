import Input from "../components/input";
import Layout from "../components/layout";
import { useStore } from "../context/store";
import { routes } from "../router";

function Lock() {
  const { masterKey, setMasterKey, navigate } = useStore();

  const onKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      navigate(routes.home);
    }
  };

  return (
    <Layout>
      <div className="flex flex-col justify-center items-center">
        <div className="w-full border-b border-gray-500 py-2">
          <h1 className="text-xl font-bold text-center mb-1">
            Secure Password Manager
          </h1>
          <p className="text-center text-xs text-gray-400">
            Keep your passwords safe and secure
          </p>
        </div>
        <div className="p-3 w-full">
          <Input
            onKeyDown={onKeyPress}
            value={masterKey}
            label="Master Key"
            type="password"
            onChange={(v) => {
              setMasterKey(v.target.value);
            }}
          />
        </div>
      </div>
    </Layout>
  );
}

export default Lock;
