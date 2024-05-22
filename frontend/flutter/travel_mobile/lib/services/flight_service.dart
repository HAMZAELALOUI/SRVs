import 'dart:convert';
import 'package:http/http.dart' as http; // For HTTP requests
import 'package:travel_mobile/models/flight.dart';

class FlightService {
  final String baseUrl = 'http://192.168.152.1:8080/srv/vols';

  Future<List<Flight>> fetchFlights() async {
    final response = await http.get(Uri.parse('$baseUrl/'));

    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((json) => Flight.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load flights');
    }
  }

  Future<List<Flight>> searchFlights(String origin, String destination, DateTime startDate, DateTime endDate) async {
    final response = await http.get(Uri.parse(
        '$baseUrl/searchByAll?origin=$origin&destination=$destination&departDate=${startDate.toIso8601String().split('T').first}&arriveeDate=${endDate.toIso8601String().split('T').first}'));

    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((json) => Flight.fromJson(json)).toList();
    } else {
      throw Exception('Failed to search flights');
    }
  }
}