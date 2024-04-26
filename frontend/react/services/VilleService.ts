import axios from 'axios';
import { Ville } from './types';

// Setup axios instance with base URL and default headers
const api = axios.create({
    baseURL: 'http://localhost:8080/srv/villes',
    headers: {
        'Content-Type': 'application/json'
    }
});

// Service object to handle API calls related to 'Ville' entities
const villeService = {
    getAllVilles: async (): Promise<Ville[]> => {
        const response = await api.get<Ville[]>('/');
        return response.data;
    },

    getVilleById: async (id: number): Promise<Ville> => {
        const response = await api.get<Ville>(`/${id}`);
        return response.data;
    },

    getByNom: async (nom: string): Promise<Ville[]> => {
        return api.get<Ville[]>(`/nom/${nom}`);
    },

    findByPays: async (pays: string): Promise<Ville[]> => {
        return api.get<Ville[]>(`/pays/${pays}`);
    },

    findByNomContainingIgnoreCase: async (nom: string): Promise<Ville[]> => {
        return api.get<Ville[]>(`/nomIg/${nom}`);
    },

    getAllVilleNames: async (): Promise<string[]> => {
        const response = await api.get<string[]>(`/noms`);
        return response.data;
    },

    saveVille: async (ville: Ville): Promise<Ville> => {
        const response = await api.post<Ville>('/', ville);
        return response.data;
    },

    updateVille: async (id: number, villeDetails: Ville): Promise<Ville> => {
        const response = await api.put<Ville>(`/${id}`, villeDetails);
        return response.data;
    },

    deleteVille: async (id: number): Promise<void> => {
        await api.delete(`/${id}`);
    },

    deleteVilleByNom: async (nom: string): Promise<void> => {
        await api.delete(`/nom/${nom}`);
    },

    deleteVilleByPays: async (pays: string): Promise<void> => {
        await api.delete(`/pays/${pays}`);
    },

    deleteAllVilles: async (): Promise<void> => {
        await api.delete('/');
    }
};

export default villeService;
