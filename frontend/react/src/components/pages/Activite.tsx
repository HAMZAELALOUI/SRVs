import React, { useState, useEffect } from "react";
import "react-datepicker/dist/react-datepicker.css";
import { useLocation } from "react-router-dom";
import DatePicker from "react-datepicker";
import { activiteService } from "../../../services/ActiviteService"; // Importez votre service
import { Activite } from "../../../services/types";

const App: React.FC = () => {
  const location = useLocation();

  const [destination, setDestination] = useState("");
  const [selectedDate, setSelectedDate] = useState(null);
  const [activites, setActivites] = useState<Activite[]>([]); // State pour stocker les activités

  useEffect(() => {
    // Utilisez useEffect pour charger les activités une fois que le composant est monté
    fetchActivites();
  }, []); // Utilisez une dépendance vide pour s'assurer que cette fonction ne s'exécute qu'une fois

  const fetchActivites = async () => {
    try {
      const activitesData = await activiteService.findAll(); // Modifiez selon vos besoins
      setActivites(activitesData);
    } catch (error) {
      console.error("Error fetching activites:", error);
    }
  };

  const handleSearch = () => {
    console.log("Searching for:", destination, selectedDate);
    // Vous pouvez implémenter votre logique de recherche ici si nécessaire
  };

  return (
    <div className="rounded-t-lg p-4 mt-40 w-full">
      <div className="rounded-lg mx-60 p-4 flex items-center border-yellow">
        <div className="mr-4">
          <svg className="h-6 w-6 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            {/* Replace with your desired SVG icon */}
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
        </div>
        <input
          type="text"
          placeholder="Where are you going?"
          value={destination}
          onChange={(e) => setDestination(e.target.value)}
          className="flex-grow bg-transparent border-0 focus:outline-none w-1/2"
        />
        <div className="ml-4 mr-2">
          <svg className="h-6 w-6 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            {/* Replace with your desired SVG icon */}
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
          </svg>
        </div>
        <DatePicker
          selected={selectedDate}
          onChange={(date) => setSelectedDate(date)}
          className="flex-grow bg-transparent border-0 focus:outline-none"
          placeholderText="Select your dates"
        />
        <button
          onClick={handleSearch}
          className="bg-blue-600 text-white font-medium px-4 py-2 rounded-lg ml-4"
        >
          Search
        </button>
      </div>
      {/* Affichez la liste des activités */}
      <div className="mt-4 mx-60">
        <h2 className="text-xl font-semibold">List of Activities:</h2>
        <ul>
          {activites.map((activite) => (
            <li key={activite.id}>
              {activite.nom} - {activite.ville} - {activite.description}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default App;
