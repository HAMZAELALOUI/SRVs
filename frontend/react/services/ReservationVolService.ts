import axios from 'axios';
import { ReservationVol, Vol } from './types';

const API_URL = 'http://localhost:8080/Gestion-Vol/ReservationVol';

const getReservationVolById = async (id: number): Promise<ReservationVol> => {
    const response = await axios.get<ReservationVol>(`${API_URL}/${id}`);
    return response.data;
};

const findByVolId = async (volId: number | undefined): Promise<ReservationVol> => {
    const response = await axios.get<ReservationVol>(`${API_URL}/vol/${volId}`);
    return response.data;
};

const deleteByVolId = async (volId: number): Promise<void> => {
    await axios.delete(`${API_URL}/vol/${volId}`);
};

const findByVol = async (vol: Vol): Promise<ReservationVol> => {
    const response = await axios.get<ReservationVol>(`${API_URL}/vol`, { data: vol });
    return response.data;
};

const updateReservationVol = async (reservationVol: ReservationVol): Promise<ReservationVol> => {
    const response = await axios.put<ReservationVol>(`${API_URL}/update`, reservationVol);
    return response.data;
};

const saveReservationVol = async (reservationVol: { volId: number | undefined }): Promise<ReservationVol> => {
    const response = await axios.post<ReservationVol>(`${API_URL}/save`, reservationVol);
    return response.data;
};

export const reservationVolService = {
    getReservationVolById,
    findByVolId,
    deleteByVolId,
    findByVol,
    updateReservationVol,
    saveReservationVol,
};
