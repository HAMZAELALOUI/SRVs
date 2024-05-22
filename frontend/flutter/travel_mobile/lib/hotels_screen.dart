import 'package:flutter/material.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:travel_mobile/hotel_details_screen.dart';
import 'package:travel_mobile/models/hotel.dart';
import 'package:travel_mobile/services/HotelService.dart';
import 'package:intl/intl.dart'; // For date formatting

class HotelsScreen extends StatefulWidget {
  const HotelsScreen({Key? key}) : super(key: key);

  @override
  _HotelsScreenState createState() => _HotelsScreenState();
}

class _HotelsScreenState extends State<HotelsScreen> {
  late Future<List<Hotel>> futureHotels;
  final TextEditingController _searchController = TextEditingController();
  String _searchQuery = "";
  DateTime? startDate;

  @override
  void initState() {
    super.initState();
    _fetchHotels();
  }

  void _fetchHotels() {
    futureHotels = HotelService().fetchHotels(query: _searchQuery);
  }

  void _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: startDate ?? DateTime.now(),
      firstDate: DateTime(2000),
      lastDate: DateTime(2100),
    );
    if (picked != null && picked != startDate) {
      setState(() {
        startDate = picked;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Hotels')),
      body: SingleChildScrollView(
        child: Column(
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.all(16),
              child: TextField(
                controller: _searchController,
                decoration: InputDecoration(
                  hintText: "Search Destination",
                  prefixIcon: const Icon(Icons.search),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(20),
                    borderSide: BorderSide.none,
                  ),
                  filled: true,
                  fillColor: Colors.grey[200],
                ),
                onChanged: (value) {
                  setState(() {
                    _searchQuery = value;
                    _fetchHotels();
                  });
                },
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: TextFormField(
                decoration: InputDecoration(
                  labelText: 'Pick Date',
                  suffixIcon: const Icon(Icons.calendar_today),
                  border: OutlineInputBorder(borderRadius: BorderRadius.circular(8)),
                  filled: true,
                  fillColor: Colors.grey[200],
                ),
                readOnly: true, // Makes the field not editable by keyboard
                controller: TextEditingController(text: startDate == null ? '' : DateFormat('yyyy-MM-dd').format(startDate!)),
                onTap: () {
                  _selectDate(context); // Triggers the date picker when the user taps on the input field
                },
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(
                top: 16, // Regular padding on top
                left: 16, // Left padding
                right: 16, // Right padding
                bottom: 32, // Double padding on bottom
              ),
              child: ElevatedButton(
                onPressed: () {
                  // Define what happens when the button is pressed, such as navigating to a new page or performing a search
                },
                child: const Text('Find Hotel'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green, // Background color of the button
                  minimumSize: const Size(double.infinity, 50), // Ensures the button stretches to fill the horizontal space
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(20), // Rounded corners
                  ),
                ),
              ),
            ),
            FutureBuilder<List<Hotel>>(
              future: futureHotels,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const Center(child: CircularProgressIndicator());
                } else if (snapshot.hasError) {
                  return Center(child: Text('Error: ${snapshot.error}'));
                } else if (!snapshot.hasData) {
                  return const Center(child: Text('No data available'));
                } else {
                  List<Hotel> hotels = snapshot.data!;
                  return ListView.builder(
                    physics: const NeverScrollableScrollPhysics(),
                    shrinkWrap: true,
                    itemCount: hotels.length,
                    itemBuilder: (context, index) {
                      return HotelCard(hotel: hotels[index]);
                    },
                  );
                }
              },
            ),
          ],
        ),
      ),
    );
  }
}

class HotelCard extends StatelessWidget {
  final Hotel hotel;

  HotelCard({required this.hotel});

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
              hotel.image,
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
                Text(hotel.nom, style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 18)),
                const SizedBox(height: 5),
                Text("${hotel.emplacement} - \$${hotel.prixChambres.toStringAsFixed(2)} per night"),
                RatingBarIndicator(
                  rating: hotel.nombreEtoiles.toDouble(),
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
                            builder: (context) => HotelDetailsScreen(hotel: hotel),
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
