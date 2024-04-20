import { useLocation } from 'react-router-dom';
import {Vol} from "../../../services/types.ts";
import React from "react";

const FlightResults: React.FC = () => {
    const location = useLocation();
    const { searchResults } = location.state as { searchResults: Vol[] }; // Cast the state to the expected type

    return (
        <div>
            <h1>Flight Results</h1>
            {searchResults.map(vol => (
                <div key={vol.id}>
                    <p>Destination: {vol.destination}</p>
                    <p>Departure Time: {new Date(vol.heureDepart).toLocaleTimeString()}</p>
                    <p>Arrival Time: {new Date(vol.heureArrivee).toLocaleTimeString()}</p>
                    <p>Price: ${vol.prix}</p>
                    <p>Available Seats: {vol.placesDisponibles}</p>
                </div>
            ))}
        </div>
    );
};

export default FlightResults;
