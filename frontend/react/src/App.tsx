import { Routes, Route, useLocation } from "react-router-dom";
//importing react slick slider
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import { animateScroll } from "react-scroll";

import NavBar from "./components/organs/NavBar"
import Home from "./components/pages/Home";
import { useEffect } from "react";
import Footer from "./components/organs/Footer";
import Hotel from "./components/pages/Hotel.tsx";
import HotelDetails from "./components/pages/HotelDetails.tsx";


function App() {
  const directory = useLocation();
  useEffect(() => {
    animateScroll.scrollToTop({
      duration: 0,
    });
  }, [directory.pathname]);

  return (
    <div className="w-full bg-white text-gray-950 font-poppins">
      <NavBar />
      <Routes>

          <Route path="/" element={<Home />} />
          <Route path="/Hotel" element={<Hotel />}/>
          <Route path="/hotel/HotelDetails/:id" element={<HotelDetails/>} />
          {/* Nouvelle route pour afficher les détails de l'hôtel */}

      </Routes>
      <Footer />
    </div>
  )
}

export default App
