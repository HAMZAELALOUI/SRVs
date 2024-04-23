import React, { useEffect, useState } from "react";
import { getUserByEmail } from "../../../services/UtilisateurServices";

const UserProfileForm: React.FC = () => {
  const [userData, setUserData] = useState<any>({});

  useEffect(() => {
    const email = sessionStorage.getItem("email");
    if (email) {
      getUserByEmail(email).then((data) => {
        if (data) {
          // Assuming the server is hosted at the same domain
          const updatedData = {
            ...data,
            profilePicture: data.profilePicture
              ? `http://localhost:8080${data.profilePicture}`
              : "https://bootdey.com/img/Content/avatar/avatar6.png",
          };
          setUserData(updatedData);
        }
      });
    }
  }, []);

  return (
    <div className="container mx-auto mt-5 bg-white p-4">
      <div className="flex flex-col items-center">
        <img
          src={userData.profilePicture}
          className="rounded-full w-52 border-4 border-gray-200 mt-5"
          alt="User avatar"
        />
        <div className="w-full mt-4 p-5 shadow-sm bg-white">
          <h4 className="text-lg font-semibold mb-4">User info</h4>
          <div className="grid grid-cols-2 gap-4">
            <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
              Full Name
            </label>
            <input
              type="text"
              name="fullName"
              value={userData.name || ""}
              onChange={(e) =>
                setUserData({ ...userData, name: e.target.value })
              }
              className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />
            <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
              Age
            </label>
            <input
              type="number"
              name="age"
              value={userData.age || ""}
              onChange={(e) =>
                setUserData({ ...userData, name: e.target.value })
              }
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
              name="mobileNumber"
              value={userData.phone || ""}
              onChange={(e) =>
                setUserData({ ...userData, name: e.target.value })
              }
              className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />
            <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
              E-mail address
            </label>
            <input
              type="email"
              name="email"
              value={userData.email || ""}
              readOnly // Email might be non-editable as it can be a primary key
              className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />
            <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
              Address
            </label>
            <textarea
              name="address"
              value={userData.address || ""}
              onChange={(e) =>
                setUserData({ ...userData, name: e.target.value })
              }
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
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserProfileForm;
