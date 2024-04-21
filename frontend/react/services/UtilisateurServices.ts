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
