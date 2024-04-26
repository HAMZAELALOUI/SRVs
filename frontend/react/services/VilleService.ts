import axios from 'axios';
import { Ville } from './types';

const API_URL = 'http://localhost:8080/srv/villes';

const getAllVilles = async (): Promise<Ville[]> => {
    const response = await axios.get<Ville[]>(`${API_URL}`);
    return response.data;
};
const getAllVilleNames = async (): Promise<string[]> => {
    try {
        const response = await axios.get<string[]>(`${API_URL}/noms`);
        return response.data;
    } catch (error) {
        console.error("Error fetching ville names:", error);
        throw error; // Renvoyer l'erreur pour la gérer à un niveau supérieur si nécessaire
    }
};

const getVilleById = async (id: number): Promise<Ville> => {
    const response = await axios.get<Ville>(`${API_URL}/${id}`);
    return response.data;
};

const getByNom = async (nom: string): Promise<Ville[]> => {
    const response = await axios.get<Ville[]>(`${API_URL}/${nom}`);
    return response.data;
};

const findByPays = async (pays: string): Promise<Ville[]> => {
    const response = await axios.get<Ville[]>(`${API_URL}/${pays}`);
    return response.data;
};

const findByNomContainingIgnoreCase = async (nom: string): Promise<Ville[]> => {
    const response = await axios.get<Ville[]>(`${API_URL}/nomIg/${nom}`);
    return response.data;
};

const saveVille = async (ville: Ville): Promise<Ville> => {
    const response = await axios.post<Ville>(`${API_URL}`, ville);
    return response.data;
};

const updateVille = async (id: number, villeDetails: Ville): Promise<Ville> => {
    const response = await axios.put<Ville>(`${API_URL}/${id}`, villeDetails);
    return response.data;
};

const deleteVille = async (id: number): Promise<void> => {
    await axios.delete(`${API_URL}/${id}`);
};

const deleteVilleByNom = async (nom: string): Promise<void> => {
    await axios.delete(`${API_URL}/${nom}`);
};

const deleteVilleByPays = async (pays: string): Promise<void> => {
    await axios.delete(`${API_URL}/${pays}`);
};

const deleteAllVilles = async (): Promise<void> => {
    await axios.delete(`${API_URL}/`);
};

export const villeService = {
    getAllVilles,
    getVilleById,
    getByNom,
    findByPays,
    findByNomContainingIgnoreCase,
    saveVille,
    updateVille,
    deleteVille,
    deleteVilleByNom,
    deleteVilleByPays,
    deleteAllVilles,
    getAllVilleNames,
};
