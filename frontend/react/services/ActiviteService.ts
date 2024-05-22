import axios from 'axios';
import { Activite } from './types'; 

const API_URL = 'http://localhost:8080/GestionVol/Activite';

const saveActivite = async (activite: Activite): Promise<number> => {
    const response = await axios.post<number>(`${API_URL}/save`, activite);
    return response.data;
};

const updateActivite = async (id: number, activite: Activite): Promise<number> => {
    const response = await axios.put<number>(`${API_URL}/update/${id}`, activite);
    return response.data;
};

const findActiviteWithVilleById = async (activiteId: number): Promise<Activite> => {
    try {
        // Form the correct URL with activiteId
        const response = await axios.get<Activite>(`${API_URL}/activity/ActiviteDetails/${activiteId}`);
        return response.data;
    } catch (error) {
        // Handle any errors that occur during the request
        console.error('Error fetching activite with ville:', error);
        throw error; // Rethrow the error to handle it further upstream
    }
};



const findByVilleAndHoraireString = async (ville: string, horaire: string): Promise<Activite[]> => {
    const response = await axios.get<Activite[]>(`${API_URL}/villeString/${ville}/horaire/${horaire}`);
    return response.data;
};

const findAll = async (): Promise<Activite[]> => {
    const response = await axios.get<Activite[]>(`${API_URL}/`);
    return response.data;
};
const findById = async (id: number): Promise<Activite> => {
    const response = await axios.get<Activite>(`${API_URL}/Id/${id}`);
    return response.data;
};

const findByNom = async (nom: string): Promise<Activite[]> => {
    const response = await axios.get<Activite[]>(`${API_URL}/nom/${nom}`);
    return response.data;
};

const findByVille = async (ville: string): Promise<Activite[]> => {
    const response = await axios.get<Activite[]>(`${API_URL}/ville/${ville}`);
    return response.data;
};

const deleteById = async (id: number): Promise<void> => {
    await axios.delete(`${API_URL}/Id/${id}`);
};

const deleteByNom = async (nom: string): Promise<void> => {
    await axios.delete(`${API_URL}/nom/${nom}`);
};

const deleteByVille = async (ville: string): Promise<void> => {
    await axios.delete(`${API_URL}/ville/${ville}`);
};
const findByHoraire = async (horaire: string): Promise<Activite[]> => {
    const response = await axios.get<Activite[]>(`${API_URL}/horaire/${horaire}`);
    return response.data;
};
const findByNomVille = async (nomVille: string): Promise<Activite[]> => {
    const response = await axios.get<Activite[]>(`${API_URL}/ville/${nomVille}`);
    return response.data;
};
export const activiteService = {
    saveActivite,
    updateActivite,
    findActiviteWithVilleById,
    findByVilleAndHoraireString,
    findAll,
    findById,
    findByNom,
    findByVille,
    deleteById,
    deleteByNom,
    deleteByVille,
    findByHoraire,
    findByNomVille,
};
