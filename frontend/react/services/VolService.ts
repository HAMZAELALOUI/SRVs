import axios from 'axios';
import { Vol } from './types'; 

const API_URL = 'http://localhost:8080/api/vols';

const getAllVols = async (): Promise<Vol[]> => {
    const response = await axios.get<Vol[]>(`${API_URL}`);
    return response.data;
};

const getVolById = async (id: number): Promise<Vol> => {
    const response = await axios.get<Vol>(`${API_URL}/id/${id}`);
    return response.data;
};

const findByDestination = async (destination: string): Promise<Vol[]> => {
    const response = await axios.get<Vol[]>(`${API_URL}/destination/${destination}`);
    return response.data;
};

const findByHeureDepart = async (heureDepart: string): Promise<Vol[]> => {
    const response = await axios.get<Vol[]>(`${API_URL}/heureDep/${heureDepart}`);
    return response.data;
};

const findByHeureArrivee = async (heureArrivee: string): Promise<Vol[]> => {
    const response = await axios.get<Vol[]>(`${API_URL}/heureArr/${heureArrivee}`);
    return response.data;
};

const findByPrix = async (prix: number): Promise<Vol[]> => {
    const response = await axios.get<Vol[]>(`${API_URL}/prix/${prix}`);
    return response.data;
};

const findByVille = async (ville: string): Promise<Vol[]> => {
    const response = await axios.get<Vol[]>(`${API_URL}/ville/${ville}`);
    return response.data;
};

const saveVol = async (vol: Vol): Promise<number> => {
    const response = await axios.post<number>(`${API_URL}`, vol);
    return response.data;
};

const updateVol = async (id: number, volDetails: Vol): Promise<Vol> => {
    const response = await axios.put<Vol>(`${API_URL}/id/${id}`, volDetails);
    return response.data;
};

const deleteVol = async (id: number): Promise<void> => {
    await axios.delete(`${API_URL}/${id}`);
};

const deleteVolByDestination = async (destination: string): Promise<void> => {
    await axios.delete(`${API_URL}/destination/${destination}`);
};

const deleteVolByHeureDepart = async (heureDepart: string): Promise<void> => {
    await axios.delete(`${API_URL}/byHeureDepart/${heureDepart}`);
};

const deleteVolByHeureArrivee = async (heureArrivee: string): Promise<void> => {
    await axios.delete(`${API_URL}/byHeureArrivee/${heureArrivee}`);
};

const deleteVolByPrix = async (prix: number): Promise<void> => {
    await axios.delete(`${API_URL}/prix/${prix}`);
};

const deleteAllVols = async (): Promise<void> => {
    await axios.delete(`${API_URL}/`);
}; 

export const volService = {
    getAllVols,
    getVolById,
    findByDestination,
    findByHeureDepart,
    findByHeureArrivee,
    findByPrix,
    findByVille,
    saveVol,
    updateVol,
    deleteVol,
    deleteVolByDestination,
    deleteVolByHeureDepart,
    deleteVolByHeureArrivee,
    deleteVolByPrix,
    deleteAllVols,
};
