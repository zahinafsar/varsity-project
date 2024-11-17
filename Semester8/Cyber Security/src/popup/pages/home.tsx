import Input from "../components/input";
import Layout from "../components/layout";
import { useStore } from "../context/store";

function Home() {
  const { navigate, setMasterKey, passwords, removePassword } = useStore();

  const add = () => navigate((r) => r.add);
  const logout = () => {
    setMasterKey("");
    navigate((r) => r.lock);
  };

  return (
    <Layout>
      <div className="flex flex-col justify-center items-center">
        <div className="w-full border-b border-gray-500 px-3 py-2 flex items-center justify-between">
          <h1 className="text-xl font-bold text-center mb-1">SPM</h1>
          <div className="flex items-center gap-3">
            <p onClick={add} className="text-xs text-green-500 cursor-pointer">
              Add
            </p>
            <p onClick={logout} className="text-xs text-red-400 cursor-pointer">
              Logout
            </p>
          </div>
        </div>
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
                      <p className="text-xs max-w-[70%] truncate">{password.url}</p>
                      <div className="flex items-center gap-2">
                        <p
                          className="text-xs text-red-400 cursor-pointer"
                          onClick={() => {
                            removePassword(password.id);
                          }}
                        >
                          Delete
                        </p>
                        {/* <p className="text-xs text-blue-400 cursor-pointer">Use</p> */}
                      </div>
                    </div>
                    <Input type="password" value={password.password} />
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
      </div>
    </Layout>
  );
}

export default Home;
