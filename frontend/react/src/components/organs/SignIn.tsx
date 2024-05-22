import React, { FC, useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../../../services/UtilisateurServices";

interface SignInProps {}

const SignIn: FC<SignInProps> = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value);
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const response = await loginUser(email, password);
        if (response.success) {
            console.log("Redirecting to home...");
            navigate("/"); // Adjust this to your actual homepage route
        } else {
            console.error("Login failed:", response.message);
        }
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
                <div className="form-container bg-white bg-opacity-30 shadow-lg rounded-lg p-8 h-96"> {/* Hauteur augmentée */}
                    <img
                        src="https://raw.githubusercontent.com/hicodersofficial/glassmorphism-login-form/master/assets/illustration.png"
                        alt="illustration" className="illustration absolute top-0 right-0 w-1/2 -mt-4" />
                    <h1 className="opacity-70 text-3xl font-semibold text-center text-gray-800 mb-6">LOGIN</h1>
                    <form onSubmit={handleSubmit}>
                        <input
                            type="text"
                            placeholder="email"
                            className="w-full px-4 py-2 mt-1 text-gray-800 bg-gray-200 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500 focus:ring focus:ring-blue-500"
                            value={email}
                            onChange={handleEmailChange}
                        />
                        <input
                            type="password"
                            placeholder="PASSWORD"
                            className="w-full px-4 py-2 mt-4 text-gray-800 bg-gray-200 border border-gray-300 rounded-md focus:outline-none focus:border-blue-500 focus:ring focus:ring-blue-500"
                            value={password}
                            onChange={handlePasswordChange}
                        />
                        <button
                            type="submit"
                            className="w-full mt-6 px-4 py-2 text-white bg-blue-500 rounded-md hover:bg-blue-600 focus:outline-none focus:bg-blue-600"
                        >
                            LOGIN
                        </button>
                    </form>
                    <div className="register-forget mt-4 opacity-70 text-sm">
                        <p className="mt-8 text-xs font-light text-center text-gray-700">
                            Don't have an account?{" "}
                            <a
                                href="#"
                                className="font-medium text-purple-600 hover:underline"
                                onClick={() => navigate("/sign-up")}
                            >
                                Sign up
                            </a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SignIn;
