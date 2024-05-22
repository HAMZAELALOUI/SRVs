class Activity {
  final String nom;
  final String lieu;
  final double prix;
  final String image;
  final String description;
  final String horaire;

  Activity({
    required this.nom,
    required this.lieu,
    required this.prix,
    required this.image,
    required this.description,
    required this.horaire,
  });

  factory Activity.fromJson(Map<String, dynamic> json) {
    return Activity(
      nom: json['nom'],
      lieu: json['lieu'],
      prix: json['prix'],
      image: json['image'],
      description: json['description'],
      horaire: json['horaire'],
    );
  }
}
