import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import "react-responsive-carousel/lib/styles/carousel.min.css";
// import { initDb } from "@/db/migration";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Note Hub",
  description: "A simple note sharing app",
};

export default async function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
//  initDb();
  return (
    <html lang="en">
      <body className={inter.className}>{children}</body>
    </html>
  );
}
