import React from "react";
import NavBar from "../organs/NavBar";
import Footer from "../organs/Footer";
import { AuthProvider } from "../AuthContext";


interface MainLayoutProps {
  children: React.ReactNode;
}


const MainLayout: React.FC<MainLayoutProps> = ({ children }) => {

  return (
    <div className="flex flex-col min-h-screen">
      <AuthProvider>
        <NavBar />
      </AuthProvider>
      <main className="flex-grow">
        {" "}
        {/* Ensure main content takes available space */}
        {children}
      </main>
      <Footer />
    </div>
  );
};

export default MainLayout;



