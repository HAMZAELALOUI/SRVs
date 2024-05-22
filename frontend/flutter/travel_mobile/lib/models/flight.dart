class Ville {
  final int id;
  final String nom;
  final String pays;

  Ville({required this.id, required this.nom, required this.pays});

  factory Ville.fromJson(Map<String, dynamic> json) {
    return Ville(
      id: json['id'],
      nom: json['nom'],
      pays: json['pays'],
    );
  }
}

class Flight {
  final int idVol;
  final Ville origin;
  final Ville destination;
  final String heureDepart;
  final String heureArrivee;
  final double prix;
  final int placesDisponibles;
  final String imageUrl;

  Flight({
    required this.idVol,
    required this.origin,
    required this.destination,
    required this.heureDepart,
    required this.heureArrivee,
    required this.prix,
    required this.placesDisponibles,
    required this.imageUrl,
  });

  factory Flight.fromJson(Map<String, dynamic> json) {
    return Flight(
      idVol: json['idVol'],
      origin: Ville.fromJson(json['origin']),
      destination: Ville.fromJson(json['destination']),
      heureDepart: json['heureDepart'],
      heureArrivee: json['heureArrivee'],
      prix: json['prix'].toDouble(),
      placesDisponibles: json['placesDisponibles'],
      imageUrl: json['imageUrl'] ?? '',
    );
  }
}
