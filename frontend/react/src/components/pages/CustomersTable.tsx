// src/components/CustomersTable.tsx
import React from "react";

interface Customer {
  id: number;
  name: string;
  address: string;
  company: string;
  balance: number;
}

// Static list of customers
const customers: Customer[] = [
  {
    id: 1,
    name: "John Doe",
    address: "123 Apple St.",
    company: "Doe Inc.",
    balance: 1200.5,
  },
  {
    id: 2,
    name: "Jane Smith",
    address: "456 Orange Ave.",
    company: "Smith Co.",
    balance: 2400.75,
  },
  // ... more customers
];

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
          {customers.map((customer) => (
            <tr key={customer.id} className="hover:bg-gray-100">
              <td className="py-3 px-4">{customer.name}</td>
              <td className="py-3 px-4">{customer.address}</td>
              <td className="py-3 px-4">{customer.company}</td>
              <td className="py-3 px-4">${customer.balance.toFixed(2)}</td>
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
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CustomersTable;
