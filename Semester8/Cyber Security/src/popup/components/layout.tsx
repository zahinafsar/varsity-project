import React from "react";
import { theme } from "../../theme";

type Props = {
  children: React.ReactNode;
};

const Layout = ({ children }: Props) => {
  return (
    <div style={{ backgroundColor: theme.primary }} className="w-[300px] dark:text-primary">
      {children}
    </div>
  );
}

export default Layout;
