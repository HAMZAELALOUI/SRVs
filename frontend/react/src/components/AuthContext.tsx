import { useState, useEffect, useContext, createContext } from "react";

// src/types/authTypes.ts
export interface User {
  name: string;
  email: string;
}

export interface AuthContextType {
  user: User | null;
  login: (token: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};

export const AuthProvider: React.FC = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const token = sessionStorage.getItem("authToken");
    const userInfo = token ? decodeToken(token) : null;
    setUser(userInfo);
  }, []);

  const login = (token: string) => {
    sessionStorage.setItem("authToken", token);
    setUser(decodeToken(token));
  };

  const logout = () => {
    sessionStorage.removeItem("authToken");
    sessionStorage.removeItem("email");

    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

function decodeToken(token: string): User | null {
  try {
    const base64Url = token.split(".")[1];
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split("")
        .map(function (c) {
          return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
        })
        .join("")
    );

    const { name, email } = JSON.parse(jsonPayload);
    return { name, email };
  } catch (e) {
    return null;
  }
}
