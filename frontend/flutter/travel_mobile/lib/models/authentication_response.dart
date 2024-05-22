class AuthenticationResponse {
  final String jwt;

  AuthenticationResponse({required this.jwt});

  factory AuthenticationResponse.fromJson(Map<String, dynamic> json) {
    return AuthenticationResponse(
      jwt: json['jwt'],
    );
  }
}
