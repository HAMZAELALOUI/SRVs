import React, { useState, useEffect } from "react";
import "react-datepicker/dist/react-datepicker.css";
import { Link } from "react-router-dom";
import DatePicker from "react-datepicker";
import { hotelService } from "../../../services/HotelService";
import { Hotel } from "../../../services/types";
import villeService from "../../../services/VilleService";
import {FiSearch} from "react-icons/fi";

const HotelList: React.FC = () => {
    const [villes, setVilles] = useState<string[]>([]);
    const [selectedVille, setSelectedVille] = useState<string>("");
    const [selectedStartDate, setSelectedStartDate] = useState<Date | null>(null);
    const [selectedEndDate, setSelectedEndDate] = useState<Date | null>(null);
    const [hotels, setHotels] = useState<Hotel[]>([]);
    const [searchTerm, setSearchTerm] = useState<string>("");
    const [prixMin, setPrixMin] = useState<number | null>(null);
    const [prixMax, setPrixMax] = useState<number | null>(null);

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

    const handleSearchByName = async () => {
        try {
            const hotelsData = await hotelService.findByNom(searchTerm);
            setHotels(hotelsData);
        } catch (error) {
            console.error("Error searching by name for hotels:", error);
        }
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
            } else if (prixMin !== null && prixMax !== null) {
                hotelsData = await hotelService.findByPrixChambresBetween(prixMin, prixMax);
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
        <div className="container mx-auto mt-20 px-4"> {/* Ajout de la marge supérieure */}

            <div className="flex justify-between items-center mb-8">
                <div className="flex items-center">
                    <div className="relative">
                        <input
                            type="text"
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            placeholder="Rechercher par nom d'hôtel"
                            className="bg-white border border-gray-300 focus:outline-none focus:border-orange-500 px-4 py-2 rounded-full shadow-md pl-10" // Ajustement de la marge à gauche
                        />
                        <FiSearch className="absolute left-3 top-3 text-gray-400" /> {/* Ajout de l'icône de recherche */}
                    </div>
                    <button
                        onClick={handleSearchByName}
                        className="ml-4 bg-gradient-to-r from-orange-300 to-orange-500 text-white font-medium px-6 py-2 rounded-full shadow-md hover:from-orange-500 hover:to-orange-700 transition duration-300 focus:outline-none"
                    >
                        Rechercher
                    </button>
                </div>
                <div className="flex items-center">
                    <select
                        value={selectedVille}
                        onChange={(e) => setSelectedVille(e.target.value)}
                        className="bg-white border border-gray-300 focus:outline-none focus:border-orange-500 px-4 py-2 rounded-full shadow-md mr-4"
                    >
                        <option value="">Sélectionnez une ville</option>
                        {villes.map((ville, index) => (
                            <option key={index} value={ville}>{ville}</option>
                        ))}
                    </select>

                    <DatePicker
                        selected={selectedStartDate}
                        onChange={handleStartDateChange}
                        className="bg-white border border-gray-300 focus:outline-none focus:border-orange-500 px-4 py-2 rounded-full shadow-md mr-4"
                        placeholderText="Sélectionnez la date de début"
                        dateFormat="yyyy-MM-dd"
                    />
                    <DatePicker
                        selected={selectedEndDate}
                        onChange={handleEndDateChange}
                        className="bg-white border border-gray-300 focus:outline-none focus:border-orange-500 px-4 py-2 rounded-full shadow-md mr-4"
                        placeholderText="Sélectionnez la date de fin"
                        dateFormat="yyyy-MM-dd"
                    />
                    <button
                        onClick={handleSearch}
                        className="bg-gradient-to-r from-orange-300 to-orange-500 text-white font-medium px-6 py-2 rounded-full shadow-md hover:from-orange-500 hover:to-orange-700 transition duration-300 focus:outline-none"
                    >
                        Rechercher
                    </button>
                </div>
            </div>
            <div className="flex justify-center mb-8">
                <div className="relative">
                    <input
                        type="text"
                        value={prixMin}
                        onChange={(e) => setPrixMin(parseFloat(e.target.value))}
                        placeholder="Prix minimum"
                        className="bg-white border border-gray-300 focus:outline-none focus:border-orange-500 px-4 py-2 rounded-full shadow-md pl-10" // Ajustement de la marge à gauche
                    />
                    <FiSearch className="absolute left-3 top-3 text-gray-400" /> {/* Ajout de l'icône de recherche */}
                </div>
                <div className="relative">
                    <input
                        type="text"
                        value={prixMax}
                        onChange={(e) => setPrixMax(parseFloat(e.target.value))}
                        placeholder="Prix maximum"
                        className="bg-white border border-gray-300 focus:outline-none focus:border-orange-500 px-4 py-2 rounded-full shadow-md pl-10" // Ajustement de la marge à gauche
                    />
                    <FiSearch className="absolute left-3 top-3 text-gray-400" /> {/* Ajout de l'icône de recherche */}
                </div>
                <button
                    onClick={handleSearch}
                    className="bg-gradient-to-r from-orange-300 to-orange-500 text-white font-medium px-6 py-2 rounded-full shadow-md hover:from-orange-500 hover:to-orange-700 transition duration-300 focus:outline-none"
                >
                    Rechercher par gamme de prix
                </button>
            </div>


            <div className="container mx-auto mt-20 px-4 mb-20">
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
                    {hotels.map((hotel) => (
                        <div key={hotel.id}
                             className="bg-orange-100 rounded-full overflow-hidden text-center shadow-orange-200 shadow-lg transition-colors duration-200 hover:bg-orange-200 hover:text-orange-900">
                            <img src={hotel.image} alt="Hotel" className="w-full h-64 object-cover rounded-full"/>
                            <div className="p-6">
                                <h3 className="text-xl font-semibold text-orange-900 mb-2">{hotel.nom}</h3>
                                <p className="text-gray-600 mb-2">{hotel.emplacement}</p>
                                <p className="text-yellow-600 font-semibold">{hotel.nombreEtoiles} étoiles</p>
                                <Link to={`/hotel/HotelDetails/${hotel.id}`}
                                      className="inline-block bg-orange-300 hover:bg-orange-700 text-white font-bold py-3 px-2 rounded-full transition-colors">
                                    Voir les détails
                                </Link>
                            </div>
                        </div>
                    ))}
                </div>
            </div>

        </div>
    );
};

export default HotelList;
