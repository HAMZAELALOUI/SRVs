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
