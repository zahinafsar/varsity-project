export const Section = ({
  children,
  className,
}: {
  children: React.ReactNode;
  className?: string;
}) => <div className={"w-full h-full " + className}>{children}</div>;
