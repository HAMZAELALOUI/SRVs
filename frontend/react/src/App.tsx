import { Routes, Route, useLocation } from "react-router-dom";
//importing react slick slider
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import { animateScroll } from "react-scroll";
import Activite from "./components/pages/Activite";

import { useEffect } from "react";
import MainLayout from "./components/layouts/MainLayout";
import Home from "./components/pages/Home";
import ActiviteDetails from "./components/layouts/ActiviteDetails";

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
      <Route
          path="/"
          element={
            <MainLayout>
              <Home />
            </MainLayout>
          }
        />
       <Route path="/activity/ActiviteDetails/:id" element={<ActiviteDetails />} />


             <Route
          path="/Activite"
          element={
            <MainLayout>
              <Activite />
            </MainLayout>
          }
        />      </Routes>
     
    </div>
  )
}

export default App
