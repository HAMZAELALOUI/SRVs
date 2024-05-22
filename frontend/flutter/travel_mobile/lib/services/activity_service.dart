import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:travel_mobile/models/activite.dart';

class ActivityService {
  final String baseUrl = 'http://192.168.152.1:8080/GestionVol/Activite';

  Future<List<Activity>> fetchActivities({String query = ""}) async {
    final String url = query.isEmpty
        ? '$baseUrl/'
        : '$baseUrl/nom/$query';
        
    final response = await http.get(Uri.parse(url));

    if (response.statusCode == 200) {
      List jsonResponse = json.decode(response.body);
      return jsonResponse.map((activity) => Activity.fromJson(activity)).toList();
    } else {
      throw Exception('Failed to load activities');
    }
  }
}
