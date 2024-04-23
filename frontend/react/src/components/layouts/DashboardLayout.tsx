import React from "react";
import { useNavigate } from 'react-router-dom';


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

    return (
        <div className="flex min-h-screen">
            {/* Sidebar */}
            <div className="w-64 bg-gray-800 text-white">
                <div className="p-5 text-xl font-medium border-b border-gray-700">
                    Admin Dashboard
                </div>
                <ul className="flex flex-col p-2">
                    <li>
                        <button onClick={HandleHomeClick} className="p-2 hover:bg-gray-700 cursor-pointer"> Home
                        </button>
                    </li>
                    <li className="p-2 hover:bg-gray-700 cursor-pointer">Users</li>
                    <li className="p-2 hover:bg-gray-700 cursor-pointer">Bookings</li>
                    <li>
                        <button onClick={HandleFlightClick} className="p-2 hover:bg-gray-700 cursor-pointer"> Flights
                        </button>
                    </li>
                    <li className="p-2 hover:bg-gray-700 cursor-pointer">Activities</li>
                    <li className="p-2 hover:bg-gray-700 cursor-pointer">Reservations</li>

                    {/* Add more menu items here */}
                </ul>
            </div>

    <main className="flex-grow">
        {" "}
        {/* Ensure main content takes available space */}
        {children}
    </main>
</div>
)
    ;
};

export default DashboardLayout;

