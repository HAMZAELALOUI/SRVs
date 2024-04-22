// src/components/FlightsTable.tsx
import React from "react";





const CustomersTable: React.FC = () => {
    return (
        <div className="container mx-auto mt-5 p-6 bg-white rounded shadow-md">
            <h1 className="text-xl font-semibold mb-5">All Customers</h1>
            <table className="min-w-full table-auto">
                <thead className="bg-gray-800 text-white">
                <tr>
                    <th className="py-3 px-4 uppercase font-semibold text-sm">Name</th>
                    <th className="py-3 px-4 uppercase font-semibold text-sm">Email</th>
                    <th className="py-3 px-4 uppercase font-semibold text-sm">Phone</th>
                    <th className="py-3 px-4 uppercase font-semibold text-sm">
                        Balance
                    </th>
                    <th className="py-3 px-4 uppercase font-semibold text-sm">
                        Actions
                    </th>
                </tr>
                </thead>
                <tbody className="text-gray-700">

                    <tr  className="hover:bg-gray-100">
                        <td className="py-3 px-4">HI</td>
                        <td className="py-3 px-4">HEY</td>
                        <td className="py-3 px-4">HELLO</td>
                        <td className="py-3 px-4">HOLA</td>
                        <td className="py-3 px-4 text-center">
                            <a href="#" className="text-indigo-600 hover:text-indigo-900">
                                Edit
                            </a>
                            <a
                                href="#"
                                className="text-indigo-600 hover:text-indigo-900 ml-10"
                            >
                                Delete
                            </a>
                        </td>
                    </tr>

                </tbody>
            </table>
        </div>
    );
};

export default CustomersTable;
