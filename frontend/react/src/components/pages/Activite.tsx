import React, { useState, useEffect } from "react";
import "react-datepicker/dist/react-datepicker.css";
import { Link } from "react-router-dom";
import { useLocation } from "react-router-dom";
import DatePicker from "react-datepicker";
import { activiteService } from "../../../services/ActiviteService"; // Importez votre service
import { Activite } from "../../../services/types";
import axios from 'axios'; // Importez axios pour les appels API

const API_URL = 'http://localhost:8080/srv/villes';

const App: React.FC = () => {
  const location = useLocation();

  const [villes, setVilles] = useState<string[]>([]); // State pour stocker les noms des villes
  const [selectedVille, setSelectedVille] = useState<string>(""); // State pour stocker la ville sélectionnée
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);
  const [activites, setActivites] = useState<Activite[]>([]); // State pour stocker les activités

  useEffect(() => {
    // Utilise useEffect pour charger les noms des villes une fois que le composant est monté
    fetchVilleNames();
    fetchActivites(); // Appeler la fonction pour charger les activités au chargement initial
  }, []); // Utilisez une dépendance vide pour s'assurer que cette fonction ne s'exécute qu'une fois

  const fetchVilleNames = async () => {
    try {
      const response = await axios.get<string[]>(`${API_URL}/noms`);
      setVilles(response.data);
    } catch (error) {
      console.error("Error fetching ville names:", error);
    }
  };

  const fetchActivites = async () => {
    try {
      const activitesData = await activiteService.findAll(); // Charger toutes les activités
      setActivites(activitesData);
    } catch (error) {
      console.error("Error fetching activites:", error);
    }
  };

  const handleSearch = async () => {
    console.log("Searching for:", selectedVille, selectedDate);
    try {
      if (!selectedVille && !selectedDate) {
        // Si les deux champs sont vides
        console.log("Both city and date are required");
        setActivites([]);
      } else if (selectedVille && !selectedDate) {
        // Si seulement la ville est sélectionnée
        const activitesData = await activiteService.findByNomVille(selectedVille);
        setActivites(activitesData);
      } else if (!selectedVille && selectedDate) {
        // Si seulement la date est sélectionnée
        const formattedDate = selectedDate.toISOString().split('T')[0];
        const activitesData = await activiteService.findByHoraire(formattedDate);
        setActivites(activitesData);
      } else if (selectedVille && selectedDate) {
        // Si les deux sont sélectionnées
        const formattedDate = selectedDate.toISOString().split('T')[0];
        const activitesData = await activiteService.findByVilleAndHoraireString(selectedVille, formattedDate);
        if (activitesData.length === 0) {
          // Si aucune activité n'est trouvée pour cette ville et cette date
          console.log("No activities found for the selected city and date");
          setActivites([]);
        } else {
          setActivites(activitesData);
        }
      }
    } catch (error) {
      console.error("Error searching for activites:", error);
    }
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
        <select
          value={selectedVille}
          onChange={(e) => setSelectedVille(e.target.value)}
          className="flex-grow bg-transparent border-0 focus:outline-none w-1/2"
        >
          <option value="">Select a city</option>
          {villes.map((ville) => (
            <option key={ville} value={ville}>{ville}</option>
          ))}
        </select>
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
          dateFormat="yyyy-MM-dd" // Format de date souhaité
        />
        <button
          onClick={handleSearch}
          className="bg-blue-600 text-white font-medium px-4 py-2 rounded-lg ml-4"
        >
          Search
        </button>
      </div>
      {/* Affiche la liste des activités */}
      <div className="mt-8 mx-10 sm:mx-20 md:mx-40 lg:mx-60">
                <h2 className="text-4xl font-extrabold mb-12 text-center">Liste des Activités</h2>
                <ul className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-12">
                    {activites.map((activite) => (
                        <li key={activite.id}
                            className="border border-gray-300 rounded-lg shadow-xl overflow-hidden transform hover:scale-105 transition duration-300">
                            <div className="relative">
                                <img src={activite.image} alt="Hotel" className="w-full h-64 object-cover"/>
                                <div className="absolute inset-0 bg-black opacity-50"></div>
                            </div>
                            <div className="p-6">
    <h3 className="text-2xl font-semibold mb-2">{activite.nom}</h3>
    <p className="text-gray-600 mb-4">{activite.lieu}</p>
    <p className="text-yellow-500 font-semibold">{activite.prix} DH</p>
    <Link to={`/activity/ActiviteDetails/${activite.id}`} className="text-blue-600 hover:underline">
    Voir les détails
</Link>

</div>
                        </li>
                    ))}
                </ul>
            </div>
            </div>
  );
};

export default App;
