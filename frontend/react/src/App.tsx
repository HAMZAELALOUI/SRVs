import { Routes, Route, useLocation } from "react-router-dom";
//importing react slick slider
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import { animateScroll } from "react-scroll";


import Home from "./components/pages/Home";
import { useEffect } from "react";
import SignUp from "./components/organs/SignUp";
import SignIn from "./components/organs/SignIn";
import MainLayout from "./components/layouts/MainLayout";
import UserProfileForm from "./components/pages/UserProfileForm";
import FlightResults from "./components/pages/FlightResults.tsx";
import FlightSearch from "./components/pages/FlightSearch.tsx";
import Dashboard from "./components/pages/Dashboard";
import RegistrationForm from "./components/pages/RegistrationForm";


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
          <Route path="/results" element={<MainLayout> <FlightResults /> </MainLayout> } />
          <Route path="/sign-up" element={<SignUp />} />
          <Route path="/sign-in" element={<SignIn />} />
          <Route path="/user-profile" element={<UserProfileForm />} />

        {/* ------------------------------ADMIN------------------ */}
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/dashboard/add-user-form" element={<RegistrationForm />} />

      </Routes>
    </div>
  );
}

export default App;
