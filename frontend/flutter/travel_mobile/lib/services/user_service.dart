import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:path/path.dart' as path;
import 'package:travel_mobile/models/user.dart';

class UserService {
  final String baseUrl = 'http://192.168.152.1:8080/srv/utilisateur';

  Future<User?> fetchUser(int userId) async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/id/$userId')).timeout(const Duration(seconds: 10));

      if (response.statusCode == 200) {
        final body = response.body;
        print('Response body: $body');
        if (body.isNotEmpty && body != 'null') {
          final Map<String, dynamic> userJson = jsonDecode(body);
          return User.fromJson(userJson);
        } else {
          print('Response body is empty or null');
          return null;
        }
      } else {
        print('Failed to load user: ${response.statusCode} ${response.body}');
        return null;
      }
    } catch (e) {
      print('Error: $e');
      return null;
    }
  }

  Future<User?> fetchUserByEmail(String email) async {
  try {
    final response = await http.get(Uri.parse('$baseUrl/email/$email')).timeout(const Duration(seconds: 10));

    if (response.statusCode == 200) {
      final body = response.body;
      print('Response body: $body');
      if (body.isNotEmpty && body != 'null') {
        final Map<String, dynamic> userJson = jsonDecode(body);
        return User.fromJson(userJson);
      } else {
        print('Response body is empty or null');
        return null;
      }
    } else {
      print('Failed to load user: ${response.statusCode} ${response.body}');
      return null;
    }
  } catch (e) {
    print('Error: $e');
    return null;
  }
}


  Future<String?> updateUser(User user, String currentPassword, {String? newPassword, String? imagePath}) async {
    try {
      var request = http.MultipartRequest('PUT', Uri.parse('$baseUrl/updateUtilisateur'));
      request.fields['id'] = user.id.toString();
      request.fields['name'] = user.name;
      request.fields['email'] = user.email;
      request.fields['phone'] = user.phone;
      request.fields['age'] = user.age.toString();
      request.fields['address'] = user.address;
      request.fields['currentPassword'] = currentPassword;
      if (newPassword != null && newPassword.isNotEmpty) {
        request.fields['newPassword'] = newPassword;
      }
      if (imagePath != null && imagePath.isNotEmpty) {
        var stream = http.ByteStream(File(imagePath).openRead());
        var length = await File(imagePath).length();
        var multipartFile = http.MultipartFile('profilePicture', stream, length, filename: path.basename(imagePath));
        request.files.add(multipartFile);
      }

      var response = await request.send();
      if (response.statusCode == 200) {
        return 'Profile updated successfully.';
      } else {
        return 'Failed to update profile';
      }
    } catch (e) {
      print('Error: $e');
      return 'Error updating profile';
    }
  }
}
