import axios from 'axios';
import {Paiement, Reservation, Reservationactivite, ReservationVol} from './types';

const API_URL = 'http://localhost:8080/Gestion-Vol/Paiement';

const getPaiementByRef = async (ref: string): Promise<Paiement> => {
    const response = await axios.get<Paiement>(`${API_URL}/Ref/${ref}`);
    return response.data;
};

const findByModePaiement = async (modePaiement: string): Promise<Paiement[]> => {
    const response = await axios.get<Paiement[]>(`${API_URL}/mode/${modePaiement}`);
    return response.data;
};

const findByMontant = async (montant: number): Promise<Paiement[]> => {
    const response = await axios.get<Paiement[]>(`${API_URL}/montant/${montant}`);
    return response.data;
};

const findByNumeroCarte = async (numeroCarte: string): Promise<Paiement[]> => {
    const response = await axios.get<Paiement[]>(`${API_URL}/numero/${numeroCarte}`);
    return response.data;
};

const findByDateExpiration = async (dateExpiration: string): Promise<Paiement[]> => {
    const response = await axios.get<Paiement[]>(`${API_URL}/date/${dateExpiration}`);
    return response.data;
};

const findByTitulaireCarte = async (titulaireCarte: string): Promise<Paiement[]> => {
    const response = await axios.get<Paiement[]>(`${API_URL}/titulaire/${titulaireCarte}`);
    return response.data;
};

const findByReservation = async (reservation: Reservation): Promise<Paiement> => {
    const response = await axios.get<Paiement>(`${API_URL}/reservation`, {
        data: reservation
    });
    return response.data;
};


const deleteByModePaiement = async (modePaiement: string): Promise<void> => {
    await axios.delete(`${API_URL}/mode/${modePaiement}`);
};

const deleteByMontant = async (montant: number): Promise<void> => {
    await axios.delete(`${API_URL}/montant/${montant}`);
};

const deleteByNumeroCarte = async (numeroCarte: string): Promise<void> => {
    await axios.delete(`${API_URL}/numero/${numeroCarte}`);
};

const deleteByDateExpiration = async (dateExpiration: string): Promise<void> => {
    await axios.delete(`${API_URL}/date/${dateExpiration}`);
};

const deleteByTitulaireCarte = async (titulaireCarte: string): Promise<void> => {
    await axios.delete(`${API_URL}/titulaire/${titulaireCarte}`);
};

const savePaiement = async (paiement: {
    titulaireCarte: string;
    numeroCarte: string;
    dateExpiration: string;
    modePaiement: string;
    reservation: Reservation;
    montant: number
}): Promise<Paiement> => {
    const response = await axios.post<Paiement>(`${API_URL}/save`, paiement);
    return response.data;
};



const savePaiementVol = async (paiement: {
    titulaireCarte: string;
    numeroCarte: string;
    dateExpiration: string;
    modePaiement: string;
    reservationVol: ReservationVol;
    montant: number
}): Promise<Paiement> => {
    const response = await axios.post<Paiement>(`${API_URL}/savevol`, paiement);
    return response.data;
};

const findByReservationVol = async (reservationVol: ReservationVol): Promise<Paiement> => {
    const response = await axios.get<Paiement>(`${API_URL}/reservationVol`, {
        data: reservationVol
    });
    return response.data;
};
const findByReservationactivite = async (reservationactivite: Reservationactivite): Promise<Paiement> => {
    const response = await axios.get<Paiement>(`${API_URL}/reservationActivite`, {
        data: reservationactivite
    });
    return response.data;
};

const savePaiementActivite = async (paiement: {
    titulaireCarte: string;
    reservationactivite: { id: number | undefined };
    numeroCarte: string;
    dateExpiration: string;
    modePaiement: string;
    montant: number
}): Promise<Paiement> => {
    const response = await axios.post<Paiement>(`${API_URL}/saveActivite`, paiement);
    return response.data;
};

export const paiementService = {
    getPaiementByRef,
    findByModePaiement,
    findByMontant,
    findByNumeroCarte,
    findByDateExpiration,
    findByTitulaireCarte,
    findByReservation,
    deleteByModePaiement,
    deleteByMontant,
    deleteByNumeroCarte,
    deleteByDateExpiration,
    deleteByTitulaireCarte,
    savePaiement,
    findByReservationVol,
    savePaiementVol,
    findByReservationactivite,
    savePaiementActivite,
};