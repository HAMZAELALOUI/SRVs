// src/components/Dashboard.tsx
import React from "react";
import UserCrud from "./UserCrud";
import CustomersTable from "./CustomersTable";

const Dashboard: React.FC = () => {
  return (
    <div className="flex min-h-screen">
      {/* Sidebar */}
      <div className="w-64 bg-gray-800 text-white">
        <div className="p-5 text-xl font-medium border-b border-gray-700">
          Admin Dashboard
        </div>
        <ul className="flex flex-col p-2">
          <li className="p-2 hover:bg-gray-700 cursor-pointer">Home</li>
          <li className="p-2 hover:bg-gray-700 cursor-pointer">Users</li>
          <li className="p-2 hover:bg-gray-700 cursor-pointer">Bookings</li>
          <li className="p-2 hover:bg-gray-700 cursor-pointer">Flights</li>
          <li className="p-2 hover:bg-gray-700 cursor-pointer">Activities</li>
          <li className="p-2 hover:bg-gray-700 cursor-pointer">Reservations</li>

          {/* Add more menu items here */}
        </ul>
      </div>

      {/* Main Content */}
      <div className="flex-1 p-8 bg-gray-100">
        <h1 className="text-3xl font-bold text-gray-800 mb-4">Overview</h1>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {/* Widgets */}
          <div className="bg-white shadow-md rounded-lg p-6">
            <h2 className="text-xl font-semibold text-gray-700">Total Users</h2>
            <p className="text-3xl font-bold">1,024</p>
            <p className="text-green-500">+10% from last week</p>
          </div>
          <div className="bg-white shadow-md rounded-lg p-6">
            <h2 className="text-xl font-semibold text-gray-700">Revenue</h2>
            <p className="text-3xl font-bold">$48,560</p>
            <p className="text-green-500">+5% from last week</p>
          </div>
          <div className="bg-white shadow-md rounded-lg p-6">
            <h2 className="text-xl font-semibold text-gray-700">Feedback</h2>
            <p className="text-3xl font-bold">320</p>
            <p className="text-red-500">-3% from last week</p>
          </div>
        </div>
        <div className="flex-1 p-8 bg-gray-100">
          <h1 className="text-3xl font-bold text-gray-800 mb-4">Overview</h1>
          <CustomersTable /> {/* Here we include the CRUD component */}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
