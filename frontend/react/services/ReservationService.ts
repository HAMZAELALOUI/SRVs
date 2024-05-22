import axios from 'axios';
import { Hotel, Reservation } from './types';

const API_URL = 'http://localhost:8080/Gestion-Vol/Reservation';

const getReservationById = async (id: number): Promise<Reservation> => {
    const response = await axios.get<Reservation>(`${API_URL}/${id}`);
    return response.data;
};

const findByHotelId = async (hotelId: number | undefined): Promise<Reservation[]> => {
    const response = await axios.get<Reservation[]>(`${API_URL}/hotel/${hotelId}`);
    return response.data;
};

const deleteReservationByHotelId = async (hotelId: number): Promise<void> => {
    await axios.delete(`${API_URL}/hotel/${hotelId}`);
};

const findByHotel = async (hotel: Hotel): Promise<Reservation> => {
    const response = await axios.get<Reservation>(`${API_URL}/hotel`, { data: hotel });
    return response.data;
};

const updateReservation = async (reservation: Reservation): Promise<Reservation> => {
    const response = await axios.put<Reservation>(`${API_URL}/update`, reservation);
    return response.data;
};


const saveReservation = async (hotelId: number | undefined): Promise<Reservation> => {
    const response = await axios.post<Reservation>(`${API_URL}/save`, { hotelId }); // Envoyer l'ID de l'hôtel dans un objet JSON
    return response.data;
};


export const reservationService = {
    getReservationById,
    findByHotelId,
    deleteReservationByHotelId,
    findByHotel,
    updateReservation,
    saveReservation,
};
