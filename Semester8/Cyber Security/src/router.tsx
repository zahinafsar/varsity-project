import { useStore } from "./popup/context/store";
import Add from "./popup/pages/add";
import Home from "./popup/pages/home";
import Lock from "./popup/pages/lock";

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
