import axios from "axios";

const BASE_URL = "http://localhost:8080/srv/utilisateur"; // Adjust if your API base URL differs

export const registerUser = async (userData: FormData): Promise<any> => {
  try {
    const response = await axios.post(`${BASE_URL}/register`, userData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return response.data; // Return the response data to handle it in your component
  } catch (error) {
    console.error("Error during registration:", error);
    throw error; // Re-throw to handle it in the component
  }
};

// src/services/authService.ts

export type LoginResponse = {
  success: boolean;
  token?: string; // If you use token-based authentication
  message?: string;
};

export const loginUser = async (
  email: string,
  password: string
): Promise<LoginResponse> => {
  try {
    const response = await axios.get(
      `http://localhost:8080/srv/utilisateur/login/email/${email}/password/${password}`,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    if (response.status === 200) {
      console.log("Login successful", response.data);
      return { success: true, token: response.data.token }; // Adjust depending on response structure
    } else {
      return { success: false, message: "Failed to login" };
    }
  } catch (error) {
    console.error("Login error:", error);
    return { success: false, message: "errror" };
  }
};
