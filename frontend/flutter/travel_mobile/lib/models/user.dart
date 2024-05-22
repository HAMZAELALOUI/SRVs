class User {
  int id;
  String name;
  String email;
  String phone;
  int age;
  String address;
  String password;
  String imagePath;

  User({
    required this.id,
    required this.name,
    required this.email,
    required this.phone,
    required this.age,
    required this.address,
    required this.password,
    this.imagePath = 'https://images.pexels.com/photos/697509/pexels-photo-697509.jpeg?auto=compress&cs=tinysrgb&w=600',
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'] != null ? json['id'] as int : 1,
      name: json['name'] != null ? json['name'] as String : '',
      email: json['email'] != null ? json['email'] as String : '',
      phone: json['phone'] != null ? json['phone'] as String : '',
      age: json['age'] != null ? json['age'] as int : 0,
      address: json['address'] != null ? json['address'] as String : '',
      password: json['password'] != null ? json['password'] as String : '',
      imagePath: json['profilePicture'] != null ? json['profilePicture'] as String : 'https://images.pexels.com/photos/697509/pexels-photo-697509.jpeg?auto=compress&cs=tinysrgb&w=600',
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'email': email,
      'phone': phone,
      'age': age,
      'address': address,
      'password': password,
      'profilePicture': imagePath,
    };
  }

  // Factory method for creating a User instance for login
  factory User.forLogin({required String email, required String password}) {
    return User(
      id: 1,  // Default or dummy value for id
      name: '',  // Default or dummy value for name
      email: email,
      phone: '',  // Default or dummy value for phone
      age: 0,  // Default or dummy value for age
      address: '',  // Default or dummy value for address
      password: password,
    );
  }

  // Method to convert login data to JSON
  Map<String, dynamic> toLoginJson() {
    return {
      'email': email,
      'password': password,
    };
  }
}
