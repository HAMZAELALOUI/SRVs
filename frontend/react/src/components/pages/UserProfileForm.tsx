import React from "react";

type Option = {
  value: string;
  label: string;
};

const countries: Option[] = [
  { value: "Belgium", label: "Belgium" },
  { value: "Canada", label: "Canada" },
  { value: "Denmark", label: "Denmark" },
  { value: "Estonia", label: "Estonia" },
  { value: "France", label: "France" },
];

const UserProfileForm: React.FC = () => {
  return (
    <div className="container mx-auto mt-5 bg-white p-4">
      <div className="flex flex-col items-center">
        <img
          src="https://bootdey.com/img/Content/avatar/avatar6.png"
          className="rounded-full w-52 border-4 border-gray-200 mt-5"
          alt="User avatar"
        />
        <div className="w-full mt-4 p-5 shadow-sm bg-white">
          <h4 className="text-lg font-semibold mb-4">User info</h4>
          <div className="grid grid-cols-2 gap-4">
            <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
              image
            </label>
            <input
              type="file"
              className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />
            <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
              Full Name
            </label>
            <input
              type="text"
              className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />
            <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
              age
            </label>
            <input
              type="number"
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
              className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />
            <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
              E-mail address
            </label>
            <input
              type="email"
              className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />
            <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
              address
            </label>
            <textarea
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
              className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />
            <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
              New password
            </label>
            <input
              type="password"
              className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />

            <div className="bg-white shadow rounded-lg p-4 mt-4">
              <div className="flex justify-end">
                <button
                  type="submit"
                  className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                >
                  Submit
                </button>
                <button
                  type="reset"
                  className="bg-gray-300 hover:bg-gray-400 text-gray-700 font-bold py-2 px-4 rounded ml-2"
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserProfileForm;
