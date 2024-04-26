import axios from 'axios';
import { Vol } from './types';

const api = axios.create({
    baseURL: 'http://localhost:8080/srv/vols',
    headers: {
        'Content-Type': 'application/json'
    }
});

const volService = {
    getAllVols: async () => {
        return api.get<Vol[]>('/');
    },

    searchFlights: async (originId: number, destinationId: number, departDate: string, returnDate: string) => {
        return api.get<Vol[]>('/search', {
            params: { originId, destinationId, departDate, returnDate }
        });
    },
    searchByAll: async (origin: string, destination: string, departDate: string, arriveeDate: string) => {
        return api.get<Vol[]>('/searchByAll', {
            params: { origin, destination, departDate, arriveeDate }
        });
    },

    getVolById: async (id: number) => {
        return api.get<Vol>(`/id/${id}`);
    },

    findByOriginId: async (originId: number) => {
        return api.get<Vol[]>(`/origin/id/${originId}`);
    },

    findByOriginNom: async (nom: string) => {
        return api.get<Vol[]>(`/origin/nom/${nom}`);
    },

    findByOriginPays: async (pays: string) => {
        return api.get<Vol[]>(`/origin/pays/${pays}`);
    },

    findByDestinationId: async (destinationId: number) => {
        return api.get<Vol[]>(`/destination/${destinationId}`);
    },

    findByDestinationNom: async (nom: string) => {
        return api.get<Vol[]>(`/destination/nom/${nom}`);
    },

    findByDestinationPays: async (pays: string) => {
        return api.get<Vol[]>(`/destination/pays/${pays}`);
    },

    findByHeureDepart: async (heureDepart: Date) => {
        const heureDepartStr = heureDepart.toISOString().split('T')[0];
        return api.get<Vol[]>(`/heureDep/${heureDepartStr}`);
    },

    findByHeureArrivee: async (heureArrivee: Date) => {
        const heureArriveeStr = heureArrivee.toISOString().split('T')[0];
        return api.get<Vol[]>(`/heureArr/${heureArriveeStr}`);
    },

    findByPrix: async (prix: number) => {
        return api.get<Vol[]>(`/prix/${prix}`);
    },

    save: async (vol: Vol) => {
        return api.post<number>('/', vol);
    },

    updateVol: async (id: number, volDetails: Vol) => {
        return api.put<Vol>(`/id/${id}`, volDetails);
    },

    deleteVol: async (id: number) => {
        return api.delete(`/${id}`);
    },

    // Assume delete by Ville involves deleting by ville id
    deleteVolByVille: async (villeId: number) => {
        return api.delete(`/ville/${villeId}`);
    },

    deleteAllVols: async () => {
        return api.delete('/');
    }
};

export default volService;
