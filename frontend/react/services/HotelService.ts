import axios from 'axios';
import { Hotel } from './types';

const API_URL = 'http://localhost:8080/Gestion-Vol/Hotel';

const getHotelById = async (id: number): Promise<Hotel> => {
    const response = await axios.get<Hotel>(`${API_URL}/${id}`);
    return response.data;
};

const findByEmplacement = async (emplacement: string): Promise<Hotel[]> => {
    const response = await axios.get<Hotel[]>(`${API_URL}/emplacement/${emplacement}`);
    return response.data;
};

const findByNombreEtoiles = async (nombreEtoiles: number): Promise<Hotel[]> => {
    const response = await axios.get<Hotel[]>(`${API_URL}/etoiles/${nombreEtoiles}`);
    return response.data;
};

const findByDateAAndDateD = async (dateA: string | null, dateD: string | null): Promise<Hotel[]> => {
    const response = await axios.get<Hotel[]>(`${API_URL}/date/${dateA}/${dateD}`);
    return response.data;
};

const saveHotel = async (hotel: Hotel): Promise<Hotel> => {
    const response = await axios.post<Hotel>(`${API_URL}/save`, hotel, {
        headers: {
            'Content-Type': 'application/json'
        }
    });
    return response.data;
};

const updateHotel = async (id: number, hotelDetails: Hotel): Promise<Hotel> => {
    const response = await axios.put<Hotel>(`${API_URL}/update/${id}`, hotelDetails, {
        headers: {
            'Content-Type': 'application/json'
        }
    });
    return response.data;
};

const deleteHotel = async (id: number): Promise<void> => {
    await axios.delete(`${API_URL}/id/${id}`);
};

const findByReservationId = async (reservationId: number): Promise<Hotel[]> => {
    const response = await axios.get<Hotel[]>(`${API_URL}/reservation/${reservationId}`);
    return response.data;
};

const findByNom = async (nom: string): Promise<Hotel[]> => {
    const response = await axios.get<Hotel[]>(`${API_URL}/nom/${nom}`);
    return response.data;
};

const deleteByEmplacement = async (emplacement: string): Promise<void> => {
    await axios.delete(`${API_URL}/emplacement/${emplacement}`);
};

const deleteByNombreEtoiles = async (nombreEtoiles: number): Promise<void> => {
    await axios.delete(`${API_URL}/etoiles/${nombreEtoiles}`);
};

const deleteByNom = async (nom: string): Promise<void> => {
    await axios.delete(`${API_URL}/nom/${nom}`);
};

const findByPrixChambresLessThan = async (prixMax: number): Promise<Hotel[]> => {
    const response = await axios.get<Hotel[]>(`${API_URL}/prixChambres/lessThan/${prixMax}`);
    return response.data;
};

const findByPrixChambresBetween = async (prixMin: number, prixMax: number): Promise<Hotel[]> => {
    const response = await axios.get<Hotel[]>(`${API_URL}/prixChambres/between/${prixMin}/${prixMax}`);
    return response.data;
};

const findByVille = async (nomVille: string): Promise<Hotel[]> => {
    const response = await axios.get<Hotel[]>(`${API_URL}/ville/nom/${nomVille}`);
    return response.data;
};

const findByNomVilleAndDateAAndDateD = async (nomVille: string, dateA: string, dateD: string): Promise<Hotel[]> => {
    const response = await axios.get<Hotel[]>(`${API_URL}/ville/${nomVille}/date/${dateA}/${dateD}`);
    return response.data;
};

const findActiviteWithVilleById = async (hotelid: number) => {
    const response = await axios.get(`${API_URL}/hotel/HotelDetails/${hotelid}`);
    return response.data;
};

const getAllHotels = async (): Promise<Hotel[]> => {
    const response = await axios.get<Hotel[]>(API_URL);
    return response.data;
};

export const hotelService = {
    findByVille,
    getAllHotels,
    getHotelById,
    findByEmplacement,
    findByNombreEtoiles,
    findByDateAAndDateD,
    saveHotel,
    updateHotel,
    deleteHotel,
    findByNomVilleAndDateAAndDateD,
    findByReservationId,
    findByNom,
    deleteByEmplacement,
    deleteByNombreEtoiles,
    deleteByNom,
    findByPrixChambresLessThan,
    findByPrixChambresBetween,
    findActiviteWithVilleById,

};
