import React from "react";

const UserProfileForm: React.FC = () => {
  return (
    <div className="container mx-auto mt-5 bg-white p-4">
      <form>
        <div className="flex flex-col items-center">
          <img
            src={""}
            className="rounded-full w-52 border-4 border-gray-200 mt-5"
            alt="User avatar"
          />
          <input type="file" />
          <div className="w-full mt-4 p-5 shadow-sm bg-white">
            <h4 className="text-lg font-semibold mb-4">User info</h4>
            <div className="grid grid-cols-2 gap-4">
              <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
                Full Name
              </label>
              <input
                type="text"
                name="name"
                className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
              <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
                Age
              </label>
              <input
                type="number"
                name="age"
                className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
          </div>

          <div className="w-full mt-4 p-5 shadow-sm bg-white">
            <h4 className="text-lg font-semibold mb-4">Contact info</h4>
            <div className="grid grid-cols-2 gap-4">
              <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
                Mobile number
              </label>
              <input
                type="tel"
                name="phone"
                className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
              <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
                E-mail address
              </label>
              <input
                type="email"
                name="email"
                readOnly
                className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
              <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
                Address
              </label>
              <textarea
                name="address"
                rows={3}
                className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              ></textarea>
            </div>
          </div>

          <div className="w-full mt-4 p-5 shadow-sm bg-white">
            <h4 className="text-lg font-semibold mb-4">Security</h4>
            <div className="grid grid-cols-2 gap-4">
              <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
                Current password
              </label>
              <input
                type="password"
                name="currentPassword"
                className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
              <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
                New password
              </label>
              <input
                type="password"
                name="newPassword"
                className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
          </div>
          <button
            type="submit"
            className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
          >
            Update Profile
          </button>
        </div>
      </form>
    </div>
  );
};

export default UserProfileForm;
