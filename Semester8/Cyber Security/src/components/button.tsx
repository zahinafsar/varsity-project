import React from "react";

interface Props extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
  onClick?: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

export default function Button({ children, onClick, className }: Props) {
  return (
    <button
      className={
        "dark:bg-blue-500 dark:text-primary hover:bg-blue-700 text-sm text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline " +
        className
      }
      type="button"
      onClick={onClick}
    >
      {children}
    </button>
  );
}
