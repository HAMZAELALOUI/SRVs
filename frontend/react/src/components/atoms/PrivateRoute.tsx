import { Navigate } from "react-router-dom";

interface PrivateRouteProps {
  children: JSX.Element;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ children }) => {
  const isAuthenticated = sessionStorage.getItem("isAuthenticated");

  if (!isAuthenticated) {
    return <Navigate to="/dashboard/login" />;
  }

  return children;
};

export default PrivateRoute;
