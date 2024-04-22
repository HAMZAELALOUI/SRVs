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
  token?: string;
  message?: string;
};

export const loginUser = async (
  email: string,
  password: string
): Promise<LoginResponse> => {
  try {
    const response = await axios.post(
      `http://localhost:8080/srv/utilisateur/login?email=${encodeURIComponent(
        email
      )}&password=${encodeURIComponent(password)}`,
      null, // No body is needed for this request
      {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      }
    );

    if (response.status === 200) {
      console.log("Login successful", response.data);

      sessionStorage.setItem("authToken", response.data.jwt);
      sessionStorage.setItem("email", email);

      return { success: true, token: response.data.jwt };
    } else {
      return { success: false, message: "Login failed" };
    }
  } catch (error: any) {
    // Note the `any` type assertion here, or better yet, handle the error more safely
    console.error("Login error:", error);
    // Check if the error structure is what we expect from axios
    if (axios.isAxiosError(error)) {
      return {
        success: false,
        message: error.response?.data?.message || "Login failed",
      };
    }
    return {
      success: false,
      message: "An unexpected error occurred",
    };
  }
};
