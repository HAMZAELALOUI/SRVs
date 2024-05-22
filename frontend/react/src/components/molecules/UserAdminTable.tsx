// src/components/FlightsTable.tsx
import React from "react";
import { Utilisateur } from "../../../services/types.ts";

interface UsersTableProps {
  users: Utilisateur[];
  onEditClick: (usr: Utilisateur) => void;
}

const UsersTable: React.FC<UsersTableProps> = ({
                                                 users,
                                                 onEditClick,
                                               }) => {



    return (
        <div className="container mx-auto mt-5 p-6 bg-white rounded shadow-md">
            <h1 className="text-xl font-semibold mb-5">User Details</h1>
            <table className="min-w-full table-auto">
                <thead className="bg-gray-800 text-white">
                <tr>
                    <th className="py-3 px-4 uppercase font-semibold text-sm text-left">Profile Picture</th>
                    <th className="py-3 px-4 uppercase font-semibold text-sm text-left">Name</th>
                    <th className="py-3 px-4 uppercase font-semibold text-sm text-left">Email</th>
                    <th className="py-3 px-4 uppercase font-semibold text-sm text-left">Phone</th>
                    <th className="py-3 px-4 uppercase font-semibold text-sm text-left">Age</th>
                    <th className="py-3 px-4 uppercase font-semibold text-sm text-left">Address</th>
                    
                </tr>
                </thead>
                <tbody className="text-gray-700">
                {users.map((user) => (
                    <tr key={user.id} className="hover:bg-gray-100">
                        <td className="py-3 px-4 rounded-full w-20 h-20 object-cover shadow-sm">
                            <img src={`http://localhost:8080${user.profilePicture}`} alt="Profile" />
                        </td>
                        <td className="py-3 px-4">{user.name}</td>
                        <td className="py-3 px-4">{user.email}</td>
                        <td className="py-3 px-4">{user.phone}</td>
                        <td className="py-3 px-4">{user.age}</td>
                        <td className="py-3 px-4">{user.address}</td>
                        
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};
export default UsersTable;
