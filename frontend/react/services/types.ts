export interface Vol {
  idVol?: number;
  origin: Ville;
  destination: Ville;
  heureDepart: Date; // Use string to represent ISO 8601 date format in TypeScript
  heureArrivee: Date; // Use string for dates in TypeScript as well
  prix: number;
  placesDisponibles: number;
  imageUrl: string;
}

export interface Ville {
  id?: number;
  nom: string;
  pays: string;
  vol: Vol;
}

export interface Utilisateur {
    id?: number;name: string;
    email: string;
    phone?: string;
    age?: number;
    address?: string;
    profilePicture?: string;
}
export interface Activite {
    id?: number;
    nom: string;
    ville: string;
    image:string;
    lieu: string;
    description: string;
    horaire: string;
    prix: number;

}
export interface Details {
    id?: number;
    activite: Activite;
/*reservation:Reservation*/
}

  }

