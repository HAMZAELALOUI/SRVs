import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { Vol } from "../../../services/types.ts";
import api from "../../../services/VolService.ts";

const FlightRes: React.FC = () => {
    const navigate = useNavigate();
    const [allFlights, setAllFlights] = useState<Vol[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await api.getAllVols();
                setAllFlights(response.data);
            } catch (error) {
                console.error("Error fetching flights:", error);
            }
        };
        fetchData();
    }, []);

    const handleDetailsClick = (vol: Vol) => {
        navigate('/flight-details', { state: { vol } });
    };

    return (
        <div className="px-4 m-60 py-2">
            <h1 className="text-2xl font-bold text-center mb-6">Flight Results</h1>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {allFlights.map(vol => (
                    <div key={vol.idVol} className="bg-white rounded-lg shadow-md p-4 flex flex-col items-center text-center">
                        <img
                            src={`http://localhost:8080${vol.imageUrl}`} // Replace with your actual image URL
                            alt="Flight"
                            className="w-full h-48 object-cover rounded-t-lg"
                        />
                        <h2 className="text-xl font-semibold my-2">Flight from {vol.origin.nom} to {vol.destination.nom}</h2>
                        <p className="mb-1">Departure Time: {new Date(vol.heureDepart).toISOString().split('T')[0]}</p>
                        <p className="mb-1">Arrival Time: {new Date(vol.heureArrivee).toISOString().split('T')[0]}</p>
                        <p className="mb-1">Price: <span className="text-green-500">${vol.prix}</span></p>
                        <p className="mb-1">Available Seats: {vol.placesDisponibles}</p>
                        {/* Additional data */}

                        <button
                            onClick={() => handleDetailsClick(vol)}
                            className="mt-4 bg-color1 hover:bg-color2 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                        >
                            Details
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default FlightRes;
