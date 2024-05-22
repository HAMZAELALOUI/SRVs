import React, {useEffect, useState} from 'react';
import VolService from '../../../services/VolService.ts';
import { useNavigate } from 'react-router-dom';
import villeService from "../../../services/VilleService.ts";
import {Ville} from "../../../services/types.ts";



interface FlightSearchProps {
    onSearch: (origin: string, destination: string, departureDate: Date, returnDate: Date) => void;
}

const FlightSearch: React.FC<FlightSearchProps> = () => {
    const [origin, setOrigin] = useState('');
    const [destination, setDestination] = useState('');
    const [departDate, setDepartDate] = useState(new Date());
    const [arriveeDate, setReturnDate] = useState(new Date());
    const [villes, setVilles] = useState<Ville[]>([]);

    useEffect(() => {
        villeService.getAllVilles()
            .then(data => {
                if (data) {
                    setVilles(data);
                } else {
                    throw new Error('No data returned');
                }
            })
            .catch(error => {
                console.error('Error fetching cities:', error);
                setVilles([]); // Maintain an empty array if there's an error
            });
    }, []);

    const navigate = useNavigate();

    const handleSearch = () => {
        const formattedDepartDate = departDate.toISOString().split('T')[0];
        const formattedReturnDate = arriveeDate.toISOString().split('T')[0];

        VolService.searchByAll(origin, destination, formattedDepartDate, formattedReturnDate)
            .then(response => {
                navigate('/flight-results', {state: {searchResults: response.data}});
            })
            .catch(error => {
                console.error('Error fetching flights:', error);
            });
    };

    return (
        <div className="flex justify-center items-center h-screen">
            <div className="bg-white bg-opacity-100 rounded-full p-20 shadow-lg flex flex-col items-center relative"
                style={{width: '600px', height: '600px'}}>
                <h2 className="text-3xl font-bold mb-4 text-center absolute top-39 ">Where do you want to
                    go?</h2>
                <div className="flex flex-col items-center justify-center flex-1">
                    <div className="flex flex-col md:flex-row gap-6">
                        <div className="flex-1">
                            <label htmlFor="origin" className="block text-gray-700 font-bold mb-2">
                                From
                            </label>
                            <select
                                id="origin"
                                value={origin}
                                onChange={(e) => setOrigin(e.target.value)}
                                className="shadow appearance-none border rounded-full w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                            >
                                <option value="">Select Origin</option>
                                {villes.map((ville) => (
                                    <option key={ville.id} value={ville.nom}>
                                        {ville.nom}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="flex-1">
                            <label htmlFor="destination" className="block text-gray-700 font-bold mb-2">
                                To
                            </label>
                            <select
                                id="destination"
                                value={destination}
                                onChange={(e) => setDestination(e.target.value)}
                                className="shadow appearance-none border rounded-full w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                            >
                                <option value="">Select Destination</option>
                                {villes.map((ville) => (
                                    <option key={ville.id} value={ville.nom}>
                                        {ville.nom}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>
                    <div className="flex flex-col md:flex-row gap-6 mt-8">
                        <div className="flex-1">
                            <label htmlFor="departDate" className="block text-gray-700 font-bold mb-2">
                                Depart Date
                            </label>
                            <input
                                type="date"
                                id="departDate"
                                value={departDate.toISOString().split('T')[0]} // Format date for input
                                onChange={(e) => setDepartDate(new Date(e.target.value))}
                                className="shadow appearance-none border rounded-full w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                            />
                        </div>
                        <div className="flex-1">
                            <label htmlFor="returnDate" className="block text-gray-700 font-bold mb-2">
                                Return Date
                            </label>
                            <input
                                type="date"
                                id="returnDate"
                                value={arriveeDate.toISOString().split('T')[0]} // Format date for input
                                onChange={(e) => setReturnDate(new Date(e.target.value))}
                                className="shadow appearance-none border rounded-full w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                            />
                        </div>
                    </div>
                </div>
                <div className="absolute inset-0 bg-transparent rounded-full border-8 border-orange-300 pointer-events-none"></div>
                <div className="mt-auto">
                    <button
                        onClick={handleSearch}
                        className="bg-orange-300 hover:bg-orange-500 text-white font-bold py-3 px-6 rounded-full focus:outline-none focus:shadow-outline"
                    >
                        Search
                    </button>
                </div>

            </div>
        </div>
    );


};

export default FlightSearch;