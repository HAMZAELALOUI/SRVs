import { useLocation } from 'react-router-dom';
import { Vol } from "../../../services/types.ts";
import React from "react";
import {Button} from "../atoms/Button.tsx";


const FlightResults: React.FC = () => {
    const location = useLocation();
    const { searchResults } = location.state as { searchResults: Vol[] };

    return (
        <div className="px-4 m-60 py-2">
            <h1 className="text-2xl font-bold text-center mb-6">Flight Results</h1>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {searchResults.map(vol => (
                    <div key={vol.idVol} className="bg-white rounded-lg shadow-md p-4 flex flex-col items-center text-center">
                        <img
                            src={vol.imageUrl} // Replace with your actual image URL
                            alt="Flight"
                            className="w-full h-48 object-cover rounded-t-lg"
                        />
                        <h2 className="text-xl font-semibold my-2">Flight to {vol.origin.nom}</h2>
                        <p className="mb-1">Departure Time: {new Date(vol.heureDepart).toLocaleTimeString()}</p>
                        <p className="mb-1">Arrival Time: {new Date(vol.heureArrivee).toLocaleTimeString()}</p>
                        <p className="mb-1">Price: <span className="text-green-500">${vol.prix}</span></p>
                        <p className="mb-1">Available Seats: {vol.placesDisponibles}</p>
                        <Button
                            type="button"
                            className="outline-none border-none lg:px-7 px-5 py-3 bg-color2 text-white font-extralight rounded-lg"
                        >
                            Details
                        </Button>

                    </div>
                ))}
            </div>
        </div>
    );
};

export default FlightResults;
