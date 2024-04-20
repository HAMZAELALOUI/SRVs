import axios from 'axios';
import {Ville, Vol} from './types';

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

    getVolById: async (id: number) => {
        return api.get<Vol>(`/id/${id}`);
    },

    findByDestination: async (destination: string) => {
        return api.get<Vol[]>(`/destination/${destination}`);
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

    findByVille: async (ville: Ville) => {
        return api.get<Vol[]>(`/ville/${ville.id}`); // Assuming ville has an id property
    },

    save: async (vol: Vol) => {
        return api.post<number>('/', vol);
    },

    updateVol: async (id: number, volDetails: Vol) => {
        return api.put<Vol>(`id/${id}`, volDetails);
    },

    deleteVol: async (id: number) => {
        return api.delete(`/id/${id}`);
    },

    deleteVolByDestination: async (destination: string) => {
        return api.delete(`/${destination}`);
    },

    deleteVolByHeureDepart: async (heureDepart: Date) => {
        const heureDepartStr = heureDepart.toISOString().split('T')[0];
        return api.delete(`/${heureDepartStr}`);
    },

    deleteVolByArrivee: async (heureArrivee: Date) => {
        const heureArriveeStr = heureArrivee.toISOString().split('T')[0];
        return api.delete(`/${heureArriveeStr}`);
    },

    deleteVolByPrix: async (prix: number) => {
        return api.delete(`/${prix}`);
    },

    deleteAllVols: async () => {
        return api.delete('/');
    }
};

export default volService;
