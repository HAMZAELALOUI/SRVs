import React, { useEffect, useState } from "react";
import axios from "axios";
import { getUserByEmail } from "../../../services/UtilisateurServices";

const UserProfileForm: React.FC = () => {
  const [userData, setUserData] = useState<any>({
    id: "", // Ensure you have id in your state if you are updating an existing user
    name: "",
    age: "",
    phone: "",
    email: "",
    address: "",
    currentPassword: "",
    newPassword: "",
    profilePicture: "https://bootdey.com/img/Content/avatar/avatar6.png",
    profilePictureFile: null,
  });

  useEffect(() => {
    const email = sessionStorage.getItem("email");
    if (email) {
      getUserByEmail(email).then((data) => {
        if (data) {
          setUserData((prevData: any) => ({
            ...prevData,
            ...data,
            profilePicture: data.profilePicture
              ? `http://localhost:8080${data.profilePicture}`
              : "https://bootdey.com/img/Content/avatar/avatar6.png",
          }));
        }
      });
    }
  }, []);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setUserData((prev: any) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      const files = e.target.files;
      setUserData((prev: any) => ({
        ...prev,
        profilePictureFile: files[0],
      }));
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("id", userData.id.toString());
    formData.append("name", userData.name);
    formData.append("age", userData.age.toString());
    formData.append("phone", userData.phone || "");
    formData.append("email", userData.email);
    formData.append("address", userData.address || "");
    formData.append("currentPassword", userData.currentPassword || "");
    formData.append(
      "newPassword",
      userData.newPassword || userData.currentPassword
    );
    if (userData.profilePictureFile) {
      formData.append("profilePicture", userData.profilePictureFile);
    }

    try {
      const response = await axios.put(
        "http://localhost:8080/srv/utilisateur/updateUtilisateur",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${sessionStorage.getItem("authToken")}`,
          },
        }
      );
      alert("Profile updated successfully!");
    } catch (error) {
      if (axios.isAxiosError(error)) {
        // Type guard to check if it's an Axios error
        const message =
          error.response?.data?.message || "An unknown error occurred";
        console.error("Failed to update profile:", message);
        alert(`Failed to update profile: ${message}`);
      } else {
        console.error("An unexpected error occurred:", error);
        alert("An unexpected error occurred");
      }
    }
  };

  return (
    <div className="container mx-auto mt-5 bg-white p-4">
      <form onSubmit={handleSubmit}>
        <div className="flex flex-col items-center">
          <img
            src={userData.profilePicture}
            className="rounded-full w-52 border-4 border-gray-200 mt-5"
            alt="User avatar"
          />
          <input type="file" onChange={handleFileChange} />
          <div className="w-full mt-4 p-5 shadow-sm bg-white">
            <h4 className="text-lg font-semibold mb-4">User info</h4>
            <div className="grid grid-cols-2 gap-4">
              <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
                Full Name
              </label>
              <input
                type="text"
                name="name"
                value={userData.name || ""}
                onChange={handleChange}
                className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
              <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
                Age
              </label>
              <input
                type="number"
                name="age"
                value={userData.age || ""}
                onChange={handleChange}
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
                value={userData.phone || ""}
                onChange={handleChange}
                className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
              <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
                E-mail address
              </label>
              <input
                type="email"
                name="email"
                value={userData.email || ""}
                readOnly
                className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
              <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
                Address
              </label>
              <textarea
                name="address"
                value={userData.address || ""}
                onChange={handleChange}
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
                value={userData.currentPassword || ""}
                onChange={handleChange}
                className="col-span-1 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
              <label className="col-span-1 text-gray-700 text-sm font-bold mb-2">
                New password
              </label>
              <input
                type="password"
                name="newPassword"
                onChange={handleChange}
                value={userData.newPassword || ""}
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
