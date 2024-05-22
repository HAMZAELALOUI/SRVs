import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/hotel.dart';

class HotelService {
  final String baseUrl = 'http://192.168.152.1:8080/Gestion-Vol/Hotel';

  Future<List<Hotel>> fetchHotels({String query = ""}) async {
    final String url = query.isEmpty
        ? '$baseUrl'
        : '$baseUrl/nom/$query';
        
    final response = await http.get(Uri.parse(url));

    if (response.statusCode == 200) {
      List jsonResponse = json.decode(response.body);
      return jsonResponse.map((hotel) => Hotel.fromJson(hotel)).toList();
    } else {
      throw Exception('Failed to load hotels');
    }
  }
}
