import { Routes, Route, useLocation } from "react-router-dom";
//importing react slick slider
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import { animateScroll } from "react-scroll";

import NavBar from "./components/organs/NavBar";
import Home from "./components/pages/Home";
import { useEffect } from "react";
import Footer from "./components/organs/Footer";
import SignUp from "./components/organs/SignUp";
import SignIn from "./components/organs/SignIn";
import MainLayout from "./components/layouts/MainLayout";
import UserProfileForm from "./components/pages/UserProfileForm";

function App() {
  const directory = useLocation();
  useEffect(() => {
    animateScroll.scrollToTop({
      duration: 0,
    });
  }, [directory.pathname]);

  return (
    <div className="w-full bg-white text-gray-950 font-poppins">
      <Routes>
        {/* <Route path="/" element={<Home />} /> */}
        <Route
          path="/"
          element={
            <MainLayout>
              <Home />
            </MainLayout>
          }
        />
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/sign-in" element={<SignIn />} />
        <Route path="/user-profile" element={<UserProfileForm />} />
      </Routes>
    </div>
  );
}

export default App;
