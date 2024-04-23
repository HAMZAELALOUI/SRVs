import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

interface DropdownProps {
  userName: string;
  logout: () => void;
}

const Dropdown: React.FC<DropdownProps> = ({ userName, logout }) => {
  const [isOpen, setIsOpen] = useState(false);
  const navigate = useNavigate();

  const toggleDropdown = () => setIsOpen(!isOpen);

  return (
    <div className="relative inline-block text-left z-50">
      <button
        onClick={toggleDropdown}
        className="inline-flex justify-center w-full rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-blue-500"
        id="menu-button"
        aria-expanded="true"
        aria-haspopup="true"
      >
        {userName} {/* Display the user's name */}
        {/* SVG icon here */}
      </button>

      {/* Dropdown panel, show/hide based on dropdown state. */}
      {isOpen && (
        <div
          className="origin-top-right absolute right-0 mt-2 w-56 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 divide-y divide-gray-100 focus:outline-none"
          role="menu"
          aria-orientation="vertical"
          aria-labelledby="menu-button"
          tabIndex={-1}
        >
          <div className="py-1" role="none">
            {/* You can add more dropdown items here */}
            <a
              href="#"
              className="text-gray-700 block px-4 py-2 text-sm"
              role="menuitem"
              tabIndex={-1}
              id="menu-item-0"
              onClick={() => {
                navigate("/user-profile");
                // Add logic to navigate to user's profile page if needed
              }}
            >
              Your Profile
            </a>
            {/* Other menu items... */}
            <a
              href="#"
              className="text-gray-700 block px-4 py-2 text-sm"
              role="menuitem"
              tabIndex={-1}
              id="menu-item-1"
              onClick={(e) => {
                e.preventDefault();
                // Add logic to navigate to settings page if needed
              }}
            >
              Settings
            </a>
            <a
              href="#"
              className="text-gray-700 block px-4 py-2 text-sm"
              role="menuitem"
              tabIndex={-1}
              id="menu-item-2"
              onClick={(e) => {
                e.preventDefault();
                logout(); // Call the logout function
              }}
            >
              Sign out
            </a>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dropdown;
