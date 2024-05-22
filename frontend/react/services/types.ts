export interface Vol {
    idVol?: number;
    origin: Ville;
    destination: Ville;
    heureDepart: string; // Utilisez string pour représenter le format de date ISO 8601 en TypeScript
    heureArrivee: string; // Utilisez également string pour les dates en TypeScript
    prix: number;
    placesDisponibles: number;
    imageUrl: string;
    reservationVol?: ReservationVol[]; // Ajout de la liste des réservations de vols
}

export interface Ville {
    id?: number;
    nom: string;
    pays: string;
    vol: Vol;
}

export interface Utilisateur {
    id?: number;
    name: string;
    email: string;
    phone?: string;
    age?: number;
    address?: string;
    profilePicture?: string;
}

export interface Activite {
    id?: number;
    nom?: string;
    ville?: Ville[];
    image:string;
    lieu: string;
    description: string;
    horaire: string;
    prix: number;
}

export interface Reservation {
    id: number;
    paiement: Paiement;
    hotel: Hotel;
    hotelId: number;
}

export interface Hotel {
    id?: number;
    nom: string;
    emplacement: string;
    description: string;
    nombreEtoiles: number;
    prixChambres: number;
    image: string;
    dateD: string; // <- Changement ici
    dateA: string; // <- Changement ici
    reservations?: Reservation[];
    ville?: Ville[];
}

export interface Paiement {
    id: number;
    ref: string;
    datePaiement: string; // Format à adapter selon le type de date utilisé (peut-être LocalDateTime en Java)

    modePaiement: string;
    montant: number;
    numeroCarte: string;
    dateExpiration: string;
    titulaireCarte: string;

    reservation: Reservation;
    reservationactivite: Reservationactivite;

}

export interface Details {
    id?: number;
    activite: Activite;
    /*reservation:Reservation*/
}

export interface ReservationVol {
    id: number;
    vol: Vol;
    volId: number;
}

export interface Reservationactivite {
    id?: number;
    activite: Activite;
    user: Utilisateur;
    dateReservation: Date;
    nombrePersonnes: number;
    prixTotal: number;

}