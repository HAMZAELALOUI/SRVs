import 'package:flutter/material.dart';
import 'package:travel_mobile/models/user.dart';
import 'flights_screen.dart';
import 'hotels_screen.dart';
import 'activities_screen.dart';
import 'user_profile_screen.dart';

class HomePage extends StatefulWidget {
  final User user;

  const HomePage({Key? key, required this.user}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> with SingleTickerProviderStateMixin {
  late TabController _tabController;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 3, vsync: this);
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Explore'),
        bottom: TabBar(
          controller: _tabController,
          tabs: const [
            Tab(icon: Icon(Icons.flight), text: 'Flights'),
            Tab(icon: Icon(Icons.hotel), text: 'Hotels'),
            Tab(icon: Icon(Icons.directions_bike), text: 'Activities'),
          ],
        ),
      ),
      body: TabBarView(
        controller: _tabController,
        children: [
          const FlightsScreen(),
          const HotelsScreen(),
          const ActivitiesScreen(),
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
        onTap: (index) {
          if (index == 2) {  // Assuming Profile is the third item
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => ProfileScreen()),
            );  
          } else {
            setState(() {
              _tabController.index = index;
            });
          }
        },
        currentIndex: _tabController.index,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(icon: Icon(Icons.list), label: 'Bookings'),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: 'Profile'),
        ],
      ),
    );
  }
}
