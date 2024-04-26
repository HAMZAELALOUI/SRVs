import React, { useState } from "react";

interface AddUserFormProps {
  onSubmit: (user: "") => void;
  onCancel: () => void;
}

const AddUserForm: React.FC<AddUserFormProps> = ({ onSubmit, onCancel }) => {
  // State for each property of User
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [age, setAge] = useState("");
  const [address, setAddress] = useState("");
  const [password, setPassword] = useState(""); // Consider not sending plain passwords
  // You can add more fields based on your User type

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
  };

  // Return form with updated fields for creating a user
  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full">
      <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
        <form className="mt-3 text-center" onSubmit={handleSubmit}>
          {/* Input fields for User properties */}
          <div>
            <label
              htmlFor="name"
              className="block text-gray-700 font-bold mb-2"
            >
              Name
            </label>
            <input
              type="text"
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
              // Other attributes as needed
            />
            {/* ... more input fields for each property of User */}
          </div>
          {/* Add more input fields similar to the above for each user attribute */}

          <button
            type="submit"
            className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
          >
            Add User
          </button>
          <button
            type="button"
            onClick={onCancel}
            className="mt-4 bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
          >
            Cancel
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddUserForm;
