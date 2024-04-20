export interface Vol {
    id?: number;
    destination: string;
    prix: number;
    heureDepart: Date; 
    heureArrivee: Date;
    placesDisponibles: number;
    villeDepart: Ville;
    VilleArrivee: Ville;
}

export interface Ville {
    id?: number;
    nom: string;
    pays: string;
    vol: Vol;
}
