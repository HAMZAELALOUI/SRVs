import React from "react";
import { useNavigate } from "react-router-dom";

interface DashboardLayoutProps {
  children: React.ReactNode;
}

const DashboardLayout: React.FC<DashboardLayoutProps> = ({ children }) => {
  const navigate = useNavigate();
  function HandleHomeClick() {
    navigate("/dashboard");
  }

  function HandleFlightClick() {
    navigate("/dashboard/flights-crud");
  }

  function handleLogout() {
    sessionStorage.removeItem("isAuthenticated"); // Remove the authentication flag
    navigate("/dashboard/login"); // Redirect to the login page
  }

  return (
    <div className="flex min-h-screen">
      {/* Sidebar */}
      <div className="w-64 bg-gray-800 text-white">
        <div className="p-5 text-xl font-medium border-b border-gray-700">
          Admin Dashboard
        </div>
        <ul className="flex flex-col p-2">
          <li>
            <button
              onClick={HandleHomeClick}
              className="p-2 hover:bg-gray-700 cursor-pointer"
            >
              {" "}
              Home
            </button>
          </li>
          <li className="p-2 hover:bg-gray-700 cursor-pointer">Users</li>
          <li className="p-2 hover:bg-gray-700 cursor-pointer">Bookings</li>
          <li>
            <button
              onClick={HandleFlightClick}
              className="p-2 hover:bg-gray-700 cursor-pointer"
            >
              {" "}
              Flights
            </button>
          </li>
          <li className="p-2 hover:bg-gray-700 cursor-pointer">Activities</li>
          <li className="p-2 hover:bg-gray-700 cursor-pointer">Reservations</li>

          {/* Add more menu items here */}
        </ul>
        {/* This div is now outside the ul but inside the sidebar div */}
        <div className="mt-auto border-t border-gray-700">
          <div className="mt-auto w-full">
            {" "}
            {/* This pushes the logout to the bottom */}
            <button
              onClick={handleLogout}
              className="w-full text-left p-2 hover:bg-gray-600 text-gray-300"
            >
              Logout
            </button>
          </div>
        </div>
      </div>

      <main className="flex-grow">
        {" "}
        {/* Ensure main content takes available space */}
        {children}
      </main>
    </div>
  );
};

export default DashboardLayout;
