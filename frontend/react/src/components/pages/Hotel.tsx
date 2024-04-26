import React, { useState, useEffect } from "react";
import "react-datepicker/dist/react-datepicker.css";
import { Link } from "react-router-dom";
import DatePicker from "react-datepicker";
import { hotelService } from "../../../services/HotelService";
import { Hotel } from "../../../services/types";
import villeService  from "../../../services/VilleService";

const HotelList: React.FC = () => {
    const [villes, setVilles] = useState<string[]>([]);
    const [selectedVille, setSelectedVille] = useState<string>("");
    const [selectedStartDate, setSelectedStartDate] = useState<Date | null>(null);
    const [selectedEndDate, setSelectedEndDate] = useState<Date | null>(null);
    const [hotels, setHotels] = useState<Hotel[]>([]);
    const [searchTerm, setSearchTerm] = useState<string>("");

    useEffect(() => {
        fetchVilleNames();
        fetchHotels();
    }, []);

    const fetchVilleNames = async () => {
        try {
            const villesData = await villeService.getAllVilleNames();
            setVilles(villesData);
        } catch (error) {
            console.error("Error fetching ville names:", error);
        }
    };

    const fetchHotels = async () => {
        try {
            const hotelsData = await hotelService.getAllHotels();
            const hotelsWithConvertedDates = hotelsData.map(hotel => ({
                ...hotel,
                datea: new Date(hotel.dateA),
                dated: new Date(hotel.dateD)
            }));
            setHotels(hotelsWithConvertedDates);
        } catch (error) {
            console.error("Error fetching hotels:", error);
        }
    };

    const handleStartDateChange = (date: Date | null) => {
        setSelectedStartDate(date);
    };

    const handleEndDateChange = (date: Date | null) => {
        setSelectedEndDate(date);
    };

    const handleSearch = async () => {
        try {
            let hotelsData: Hotel[] = [];
            const formattedStartDate = selectedStartDate ? formatDate(selectedStartDate) : null;
            const formattedEndDate = selectedEndDate ? formatDate(selectedEndDate) : null;

            if (selectedVille && formattedStartDate && formattedEndDate) {
                hotelsData = await hotelService.findByNomVilleAndDateAAndDateD(selectedVille, formattedStartDate, formattedEndDate);
            } else if (selectedStartDate && selectedEndDate) {
                hotelsData = await hotelService.findByDateAAndDateD(formattedStartDate, formattedEndDate);
            } else if (selectedVille) {
                hotelsData = await hotelService.findByVille(selectedVille);
            } else if (searchTerm) {
                hotelsData = await hotelService.findByNom(searchTerm);
            } else {
                console.error("Invalid selection");
            }
            setHotels(hotelsData);
        } catch (error) {
            console.error("Error searching for hotels:", error);
        }
    };

    const formatDate = (date: Date): string => {
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, "0");
        const day = date.getDate().toString().padStart(2, "0");
        return `${year}-${month}-${day}`;
    };

    return (
        <div className="rounded-t-lg p-4 mt-40 w-full">
            <div className="rounded-lg mx-60 p-4 flex items-center border-yellow">
                <div className="mr-4">
                    <svg className="h-6 w-6 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2}
                              d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                    </svg>
                </div>
                <select
                    value={selectedVille}
                    onChange={(e) => setSelectedVille(e.target.value)}
                    className="flex-grow bg-transparent border-0 focus:outline-none w-1/2"
                >
                    <option value="">Select a city</option>
                    {villes.map((ville, index) => (
                        <option key={index} value={ville}>{ville}</option>
                    ))}
                </select>

                <div className="ml-4 mr-2">
                    <svg className="h-6 w-6 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2}
                              d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                    </svg>
                </div>
                <DatePicker
                    selected={selectedStartDate}
                    onChange={handleStartDateChange}
                    className="flex-grow bg-transparent border-0 focus:outline-none"
                    placeholderText="Select start date"
                    dateFormat="yyyy-MM-dd"
                />
                <DatePicker
                    selected={selectedEndDate}
                    onChange={handleEndDateChange}
                    className="flex-grow bg-transparent border-0 focus:outline-none"
                    placeholderText="Select end date"
                    dateFormat="yyyy-MM-dd"
                />
                <input
                    type="text"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    placeholder="Search by hotel name"
                    className="flex-grow bg-transparent border-0 focus:outline-none w-1/2"
                />
                <button
                    onClick={handleSearch}
                    className="bg-blue-600 text-white font-medium px-4 py-2 rounded-lg ml-4"
                >
                    Search
                </button>
            </div>
            <div className="mt-8 mx-10 sm:mx-20 md:mx-40 lg:mx-60">
                <h2 className="text-4xl font-extrabold mb-12 text-center">Liste des Hôtels</h2>
                <ul className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-12">
                    {hotels.map((hotel) => (
                        <li key={hotel.id}
                            className="border border-gray-300 rounded-lg shadow-xl overflow-hidden transform hover:scale-105 transition duration-300">
                            <div className="relative">
                                <img src={hotel.image} alt="Hotel" className="w-full h-64 object-cover"/>
                            </div>
                            <div className="p-6">
                                <h3 className="text-2xl font-semibold mb-2">{hotel.nom}</h3>
                                <p className="text-gray-600 mb-4">{hotel.emplacement}</p>
                                <p className="text-yellow-500 font-semibold">{hotel.nombreEtoiles} étoiles</p>
                                <Link to={`/hotel/HotelDetails/${hotel.id}`} className="text-blue-600 hover:underline">
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

export default HotelList;
