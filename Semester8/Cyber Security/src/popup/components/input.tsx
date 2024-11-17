import React from "react";

interface Props extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  placeholder?: string;
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

export default function Input({
  label,
  onChange,
  placeholder,
  type,
  value,
  className,
  ...props
}: Props) {
  return (
    <div className={className}>
      <label className="block text-gray-500 text-sm font-bold mb-1">
        {label}
      </label>
      <input
        className="rounded text-sm w-full py-2 px-2 bg-neutral-900"
        type={type}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        {...props}
      />
    </div>
  );
}
