import { useStore } from "./context/store";
import Add from "./pages/add";
import Home from "./pages/home";
import Lock from "./pages/lock";

export const routes = {
  home: "home",
  lock: "lock",
  add: "add",
};

const Router = ({}) => {
  const { route } = useStore();
  if (route === routes.lock) return <Lock />;
  if (route === routes.home) return <Home />;
  if (route === routes.add) return <Add />;
  else return <p>Not found</p>;
};

export default Router;
