import { useLocation, useNavigate } from 'react-router-dom';
import { Vol } from "../../../services/types.ts";
import React from "react";

const FlightResults: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const { searchResults } = location.state as { searchResults: Vol[] };

    const handleDetailsClick = (vol: Vol) => {
        navigate('/flight-details', { state: { vol } });
    };

    return (
        <div className="flex justify-center items-center h-screen pt-20">
            <div className="font-sans relative sm:max-w-sm w-full">
                <div className="card bg-orange-500 shadow-lg w-full h-full rounded-3xl absolute transform -rotate-6 shadow-orange-400"></div>
                <div className="card bg-orange-300 shadow-lg w-full h-full rounded-3xl absolute transform rotate-6  "></div>
                <div className="relative w-full px-6 py-4 bg-gray-100 shadow-md">
                    <div className="rounded-full overflow-hidden border-4 border-orange-500">
                        {searchResults.map(vol => (
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
            </div>
        </div>
    );
};

export default FlightResults;