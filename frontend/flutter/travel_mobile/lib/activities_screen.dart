import 'package:flutter/material.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:travel_mobile/activity_details_screen.dart';
import 'package:travel_mobile/models/activite.dart';
import 'package:travel_mobile/services/activity_service.dart';

class ActivitiesScreen extends StatefulWidget {
  const ActivitiesScreen({Key? key}) : super(key: key);

  @override
  _ActivitiesScreenState createState() => _ActivitiesScreenState();
}

class _ActivitiesScreenState extends State<ActivitiesScreen> {
  late Future<List<Activity>> futureActivities;
  String _searchQuery = "";

  @override
  void initState() {
    super.initState();
    futureActivities = ActivityService().fetchActivities();
  }

  void _searchActivities(String query) {
    setState(() {
      _searchQuery = query;
      futureActivities = ActivityService().fetchActivities(query: _searchQuery);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Activities'),
      ),
      body: FutureBuilder<List<Activity>>(
        future: futureActivities,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('No data available'));
          } else {
            List<Activity> activities = snapshot.data!;
            return SingleChildScrollView(
              child: Column(
                children: <Widget>[
                  Padding(
                    padding: const EdgeInsets.all(16),
                    child: TextField(
                      decoration: InputDecoration(
                        hintText: "Search Activities",
                        prefixIcon: const Icon(Icons.search),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(20),
                          borderSide: BorderSide.none,
                        ),
                        filled: true,
                        fillColor: Colors.grey[200],
                      ),
                      onSubmitted: (query) => _searchActivities(query),
                    ),
                  ),
                  ListView.builder(
                    physics: const NeverScrollableScrollPhysics(),
                    shrinkWrap: true,
                    itemCount: activities.length,
                    itemBuilder: (context, index) {
                      return ActivityCard(activity: activities[index]);
                    },
                  ),
                ],
              ),
            );
          }
        },
      ),
    );
  }
}

class ActivityCard extends StatelessWidget {
  final Activity activity;

  ActivityCard({required this.activity});

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(8),
      elevation: 4,
      child: Column(
        children: [
          ClipRRect(
            borderRadius: const BorderRadius.vertical(top: Radius.circular(4)),
            child: Image.network(
              activity.image,
              width: double.infinity,
              height: 200,
              fit: BoxFit.cover,
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(activity.nom, style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 18)),
                const SizedBox(height: 5),
                Text("${activity.lieu} - \$${activity.prix.toStringAsFixed(2)} per activity"),
                Text(activity.description),
                RatingBarIndicator(
                  rating: 4.0,  // Since there's no rating in your model, use a static value or calculate if possible
                  itemBuilder: (context, _) => const Icon(Icons.star, color: Colors.amber),
                  itemCount: 5,
                  itemSize: 20.0,
                  direction: Axis.horizontal,
                ),
                const SizedBox(height: 10),
                Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    ElevatedButton(
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => ActivityDetailsScreen(activity: activity),
                          ),
                        );
                      },
                      style: ElevatedButton.styleFrom(backgroundColor: Colors.green),
                      child: const Text('Book Now'),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
