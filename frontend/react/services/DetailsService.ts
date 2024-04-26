import axios from 'axios';
import { Details } from './types'; 

const API_URL = 'http://localhost:8080/Gestion-Vol/Details';

const findByNombreParticipants = async (nombreParticipants: number): Promise<Details[]> => {
    const response = await axios.get<Details[]>(`${API_URL}/participants/${nombreParticipants}`);
    return response.data;
};

const findByPrixTotalActivite = async (prixTotalActivite: number): Promise<Details[]> => {
    const response = await axios.get<Details[]>(`${API_URL}/prix/${prixTotalActivite}`);
    return response.data;
};

const deleteByNombreParticipants = async (nombreParticipants: number): Promise<void> => {
    await axios.delete(`${API_URL}/participants/${nombreParticipants}`);
};

const deleteByPrixTotalActivite = async (prixTotalActivite: number): Promise<void> => {
    await axios.delete(`${API_URL}/prix/${prixTotalActivite}`);
};

// Ajoutez d'autres méthodes de service web ici si nécessaire

export const detailsService = {
    findByNombreParticipants,
    findByPrixTotalActivite,
    deleteByNombreParticipants,
    deleteByPrixTotalActivite,
};
