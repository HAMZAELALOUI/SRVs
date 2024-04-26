import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { hotelService } from "../../../services/HotelService";
import { Hotel } from "../../../services/types";

const HotelDetails: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [hotel, setHotel] = useState<Hotel | null>(null);

    useEffect(() => {
        fetchHotelDetails();
    }, []);

    const fetchHotelDetails = async () => {
        try {
            const hotelData = await hotelService.findActiviteWithVilleById(parseInt(id ?? ""));
            setHotel(hotelData);
        } catch (error) {
            console.error("Error fetching hotel details:", error);
        }
    };

    if (!hotel) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>{hotel.nom}</h2>
            <p>Emplacement: {hotel.emplacement}</p>
            <p>Description: {hotel.description}</p>
            <p>Nombre d'étoiles: {hotel.nombreEtoiles}</p>
            <p>Prix des chambres: {hotel.prixChambres}</p>
            <p>Date de départ: {hotel.dateD}</p>
            <p>Date d'arrivée: {hotel.dateA}</p>
            <img src={hotel.image} alt="Hotel" />
        </div>
    );
};

export default HotelDetails;
