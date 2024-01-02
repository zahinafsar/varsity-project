"use client";
import logoutUser from "@/app/actions/logoutUser";
import { FiLogOut } from "react-icons/fi";

export const LogoutButton = () => {
  const handleLogout = async () => {
    await logoutUser("user");
  };

  return (
    <button className="btn btn-error btn-outline text-white" onClick={handleLogout}>
      Logout
      <FiLogOut />
    </button>
  );
};
