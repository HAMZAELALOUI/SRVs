import { Routes, Route, useLocation } from "react-router-dom";
//importing react slick slider
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import { animateScroll } from "react-scroll";

import Home from "./components/pages/Home";
import { useEffect } from "react";
import FlightSearch from "./components/pages/FlightSearch";
import MainLayout from "./components/layouts/MainLayout.tsx";
import FlightResults from "./components/pages/FlightResults.tsx";

function App() {
  const directory = useLocation();
  useEffect(() => {
    animateScroll.scrollToTop({
      duration: 0,
    });
  }, [directory.pathname]);

  const handleFlightSearch = (origin: string, destination: string, departureDate: Date, returnDate: Date) => {
    // Implement your search logic here
    console.log("Searching flights:", origin, destination, departureDate, returnDate);
    // You can use these values to make API calls, redirect, etc.
  };

  return (
    <div className="w-full bg-white text-gray-950 font-poppins">
      <Routes>
          <Route path="/" element={<MainLayout> <Home /> </MainLayout> } />
          <Route path="/flight" element={<MainLayout> <FlightSearch onSearch={handleFlightSearch} /> </MainLayout>} />
          <Route path="/results" element={<FlightResults />} />
      </Routes>
    </div>
  )
}

export default App
