import 'dart:convert';

class Hotel {
  final int id;
  final String nom;
  final String image;
  final String emplacement;
  final String description;
  final int nombreEtoiles;
  final double prixChambres;

  Hotel({
    required this.id,
    required this.nom,
    required this.image,
    required this.emplacement,
    required this.description,
    required this.nombreEtoiles,
    required this.prixChambres,
  });

  factory Hotel.fromJson(Map<String, dynamic> json) {
    return Hotel(
      id: json['id'],
      nom: json['nom'],
      image: json['image'],
      emplacement: json['emplacement'],
      description: json['description'],
      nombreEtoiles: json['nombreEtoiles'],
      prixChambres: json['prixChambres'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nom': nom,
      'image': image,
      'emplacement': emplacement,
      'description': description,
      'nombreEtoiles': nombreEtoiles,
      'prixChambres': prixChambres,
    };
  }
}
