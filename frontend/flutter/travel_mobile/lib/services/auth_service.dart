import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:path/path.dart' as path;
import 'package:travel_mobile/models/user.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AuthService {
  final String baseUrl = 'http://192.168.152.1:8080/srv/utilisateur';

  Future<User?> login(User utilisateur) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/login'),
        headers: <String, String>{
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: {
          'email': utilisateur.email,
          'password': utilisateur.password,
        },
      ).timeout(const Duration(seconds: 10)); // Adding a timeout of 10 seconds

      if (response.statusCode == 200) {
        final body = response.body;
        if (body.isNotEmpty && body != 'null') {
          User user = User.fromJson(jsonDecode(body));
          user.email = utilisateur.email; // Save the logged-in email
          
          // Store email locally
          SharedPreferences prefs = await SharedPreferences.getInstance();
          await prefs.setString('email', utilisateur.email);

          return user;
        } else {
          print('Response body is empty or null');
          return null;
        }
      } else {
        print('Failed to login: ${response.statusCode} ${response.body}');
        return null;
      }
    } catch (e) {
      print('Error: $e');
      return null;
    }
  }

  Future<String?> register({
    required String name,
    required String email,
    required String phone,
    required int age,
    required String address,
    required String password,
    File? profilePicture,
  }) async {
    try {
      var request = http.MultipartRequest('POST', Uri.parse('$baseUrl/register'));
      request.fields['name'] = name;
      request.fields['email'] = email;
      request.fields['phone'] = phone;
      request.fields['age'] = age.toString();
      request.fields['address'] = address;
      request.fields['password'] = password;

      if (profilePicture != null) {
        var stream = http.ByteStream(profilePicture.openRead());
        var length = await profilePicture.length();
        var multipartFile = http.MultipartFile(
          'profilePicture',
          stream,
          length,
          filename: path.basename(profilePicture.path),
        );
        request.files.add(multipartFile);
      }

      var response = await request.send();

      if (response.statusCode == 200) {
        return 'User registered successfully.';
      } else if (response.statusCode == 400) {
        return 'User already exists.';
      } else {
        print('Failed to register: ${response.statusCode}');
        return 'Error registering user.';
      }
    } catch (e) {
      print('Error: $e');
      return 'Error registering user.';
    }
  }
}
