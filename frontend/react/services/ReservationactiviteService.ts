import axios from 'axios';
import { Reservationactivite } from './types'; // Supposons que vous avez un type Reservation

const API_URL = 'http://localhost:8080/GestionVol/Reservationactivite'; // Ajustez l'URL si nécessaire

// Fonction pour sauvegarder une réservation
const saveReservation = async (reservation: Reservationactivite): Promise<Reservationactivite> => {
  const response = await axios.post<Reservationactivite>(`${API_URL}/save`, reservation);
  return response.data;
};

// Vous pouvez ajouter d'autres fonctions pour interagir avec votre API Spring Boot ici, par exemple, pour récupérer une réservation par ID

export const reservationService = {
  saveReservation,
  // Ajoutez d'autres fonctions pour les opérations de réservation
};
