export interface Vol {
    id?: number;
    destination: string;
    prix: number;
    heureDepart: Date;
    heureArrivee: Date;
    placesDisponibles: number;
}

export interface Ville {
    id?: number;
    nom: string;
    pays: string;
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



export interface Reservation {
    id?: number;
    // Définir les autres propriétés de la réservation ici si nécessaire
}

