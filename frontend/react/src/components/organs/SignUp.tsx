import React, { FC, useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerUser } from "../../../services/UtilisateurServices"; // Adjust the path according to your file structure

interface SignUpProps {}

const SignUp: FC<SignUpProps> = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [age, setAge] = useState("");
  const [address, setAddress] = useState("");
  const [password, setPassword] = useState("");
  const [profilePicture, setProfilePicture] = useState<File | null>(null);
  const navigate = useNavigate();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const formData = new FormData();
    formData.append("name", name);
    formData.append("email", email);
    formData.append("phone", phone);
    formData.append("age", age);
    formData.append("address", address);
    formData.append("password", password);
    if (profilePicture) {
      formData.append("profilePicture", profilePicture);
    }

    try {
      await registerUser(formData);
      navigate("/sign-in"); // Redirect after successful registration
    } catch (error) {
      console.error("Error during registration:", error);
    }
  };

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files ? event.target.files[0] : null;
    setProfilePicture(file);
  };

  return (
      <div className="min-h-screen flex items-center justify-center bg-orange-200">
        <div className="login-container relative w-96">
          <div className="theme-btn-container absolute top-0 left-0 right-0 flex justify-between px-4 py-2">
            <div className="theme-btn bg-purple-900"></div>
            <div className="theme-btn bg-purple-800"></div>
            <div className="theme-btn bg-purple-700"></div>
            <div className="theme-btn bg-yellow-300"></div>
            <div className="theme-btn bg-red-400"></div>
            <div className="theme-btn bg-gray-900"></div>
          </div>
          <div className="circle circle-one absolute top-0 left-0"></div>
          <div className="circle circle-two absolute bottom-0 right-0"></div>
          <div className="form-container bg-white bg-opacity-30 shadow-lg rounded-lg p-8 h-120">
            <img
                src="https://raw.githubusercontent.com/hicodersofficial/glassmorphism-login-form/master/assets/illustration.png"
                alt="illustration"
                className="illustration absolute top-0 right-0 w-1/2 -mt-4"
            />
            <h1 className="opacity-70 text-3xl font-semibold text-center text-gray-800 mb-6">
              SIGN UP
            </h1>
            <form onSubmit={handleSubmit}>
              <input
                  type="text"
                  placeholder="Full Name"
                  className="w-full px-4 py-2 mt-1 text-gray-800 bg-gray-200 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500 focus:ring focus:ring-blue-500"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
              />
              <input
                  type="email"
                  placeholder="Email"
                  className="w-full px-4 py-2 mt-4 text-gray-800 bg-gray-200 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500 focus:ring focus:ring-blue-500"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
              />
              <input
                  type="number"
                  placeholder="Phone Number"
                  className="w-full px-4 py-2 mt-4 text-gray-800 bg-gray-200 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500 focus:ring focus:ring-blue-500"
                  value={phone}
                  onChange={(e) => setPhone(e.target.value)}
              />
              <input
                  type="number"
                  placeholder="Age"
                  className="w-full px-4 py-2 mt-4 text-gray-800 bg-gray-200 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500 focus:ring focus:ring-blue-500"
                  value={age}
                  onChange={(e) => setAge(e.target.value)}
              />
              <input
                  type="text"
                  placeholder="Address"
                  className="w-full px-4 py-2 mt-4 text-gray-800 bg-gray-200 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500 focus:ring focus:ring-blue-500"
                  value={address}
                  onChange={(e) => setAddress(e.target.value)}
              />
              <input
                  type="file"
                  onChange={handleFileChange}
                  className="w-full px-4 py-2 mt-4 text-gray-800 bg-gray-200 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500 focus:ring focus:ring-blue-500"
              />
              <input
                  type="password"
                  placeholder="Password"
                  className="w-full px-4 py-2 mt-4 text-gray-800 bg-gray-200 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500 focus:ring focus:ring-blue-500"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
              />
              <button
                  type="submit"
                  className="w-full mt-6 px-4 py-2 text-white bg-purple-500 rounded-md hover:bg-purple-600 focus:outline-none focus:bg-purple-600"
              >
                Register
              </button>
            </form>
            <div className="register-forget mt-4 opacity-70 text-sm">
              <p className="mt-8 text-xs font-light text-center text-gray-700">
                Do you have an account?{" "}
                <a
                    href="#"
                    className="font-medium text-color5 hover:underline"
                    onClick={() => navigate("/sign-in")}
                >
                  Sign In
                </a>
              </p>
            </div>
          </div>
        </div>
      </div>
  );
};

export default SignUp;
