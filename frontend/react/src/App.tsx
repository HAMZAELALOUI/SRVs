import { Routes, Route, useLocation } from "react-router-dom";
//importing react slick slider
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import { animateScroll } from "react-scroll";
import Activite from "./components/pages/Activite";



import ActiviteDetails from "./components/layouts/ActiviteDetails";
import Home from "./components/pages/Home";
import { useEffect } from "react";

import Footer from "./components/organs/Footer";
import Hotel from "./components/pages/Hotel.tsx";

import HotelDetails from "./components/pages/HotelDetails.tsx";
import SignUp from "./components/organs/SignUp";
import SignIn from "./components/organs/SignIn";
import MainLayout from "./components/layouts/MainLayout";
import UserProfileForm from "./components/pages/UserProfileForm";
import FlightResults from "./components/pages/FlightResults.tsx";
import FlightSearch from "./components/pages/FlightSearch.tsx";
import RegistrationForm from "./components/pages/RegistrationForm";
import AdminFlight from "./components/pages/AdminFlights.tsx";
import DashboardLayout from "./components/layouts/DashboardLayout.tsx";
import Overview from "./components/organs/Overview.tsx";
import FlightDetails from "./components/pages/FlightDetails.tsx";
import AdminLogin from "./components/pages/AdminLogin.tsx";
import PrivateRoute from "./components/atoms/PrivateRoute.tsx";
import AdminUsers from "./components/pages/AdminUsers.tsx";



function App() {
  const directory = useLocation();
  useEffect(() => {
    animateScroll.scrollToTop({
      duration: 0,
    });
  }, [directory.pathname]);

  const handleFlightSearch = (
    origin: string,
    destination: string,
    departureDate: Date,
    returnDate: Date
  ) => {
    // Implement your search logic here
    console.log(
      "Searching flights:",
      origin,
      destination,
      departureDate,
      returnDate
    );
    // You can use these values to make API calls, redirect, etc.
  };

  return (
    <div className="w-full bg-white text-gray-950 font-poppins">

      <Routes>

          <Route path="/Hotel" element={<MainLayout> <Hotel /> </MainLayout>}/>
          <Route path="/hotel/HotelDetails/:id" element={<MainLayout> <HotelDetails/> </MainLayout>} />
          {/* Nouvelle route pour afficher les détails de l'hôtel */}

        <Route
          path="/"
          element={
            <MainLayout>
              {" "}
              <Home />{" "}
            </MainLayout>
          }
        />
          <Route path="/activity/ActiviteDetails/:id" element={<MainLayout> <ActiviteDetails /> </MainLayout>} />



             <Route
          path="/Activite"
          element={
            <MainLayout>
              <Activite />
            </MainLayout>
          }
        /> 
        <Route
          path="/flight"
          element={
            <MainLayout>
              {" "}
              <FlightSearch onSearch={handleFlightSearch} />{" "}
            </MainLayout>
          }
        />
        <Route
          path="/flight-results"
          element={
            <MainLayout>
              {" "}
              <FlightResults />{" "}
            </MainLayout>
          }
        />
        <Route
          path="/flight-details"
          element={
            <MainLayout>
              {" "}
              <FlightDetails />{" "}
            </MainLayout>
          }
        />
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/sign-in" element={<SignIn />} />
        <Route path="/user-profile" element={<UserProfileForm />} />

        {/* ------------------------------ADMIN------------------ */}
        <Route
          path="/dashboard"
          element={
            <PrivateRoute>
              <DashboardLayout>
                <Overview />
              </DashboardLayout>
            </PrivateRoute>
          }
        />
        <Route
          path="/dashboard/flights-crud"
          element={
            <PrivateRoute>
              <DashboardLayout>
                <AdminFlight />
              </DashboardLayout>
            </PrivateRoute>
          }
        />
        <Route
          path="/dashboard/user-crud"
          element={
            <PrivateRoute>
              <DashboardLayout>
                <AdminUsers />
              </DashboardLayout>
            </PrivateRoute>
          }
        />
        <Route path="/dashboard/login" element={<AdminLogin />} />

        <Route path="/dashboard/add-user-form" element={<RegistrationForm />} />

      </Routes>
    </div>
  );
}

export default App;
