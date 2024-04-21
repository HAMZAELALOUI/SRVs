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
export interface Activite {
    id?: number;
    nom: string;
    ville: string;
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