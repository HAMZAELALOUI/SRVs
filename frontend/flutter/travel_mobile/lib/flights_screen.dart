import 'package:flutter/material.dart';
import 'package:intl/intl.dart'; // For date formatting
import 'dart:math'; // For generating random data
import 'package:http/http.dart' as http; // For HTTP requests
import 'package:travel_mobile/flight_details_screen.dart';
import 'package:travel_mobile/services/flight_service.dart';
import 'dart:convert'; // For JSON decoding
import 'models/flight.dart';



// Service class to fetch flights from the API

class FlightsScreen extends StatelessWidget {
  const FlightsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Column(  
          children: [
            const Padding(
              padding: EdgeInsets.all(16.0),
              child: FlightSearchForm(),
            ),
            const SizedBox(height: 20),
            SizedBox(
              height: MediaQuery.of(context).size.height - 200,
              child: const FlightResults(),
            ),
          ],
        ),
      ),
    );
  }
}

// Flight search form widget
class FlightSearchForm extends StatefulWidget {
  const FlightSearchForm({super.key});

  @override
  _FlightSearchFormState createState() => _FlightSearchFormState();
}

class _FlightSearchFormState extends State<FlightSearchForm> {
  final TextEditingController _fromController = TextEditingController();
  final TextEditingController _toController = TextEditingController();
  DateTime? _startDate;
  DateTime? _endDate;
  final TextEditingController _dateRangeController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        TextFormField(
          controller: _fromController,
          decoration: const InputDecoration(
            labelText: 'From',
            border: OutlineInputBorder(),
            suffixIcon: Icon(Icons.flight_takeoff),
            filled: true,
          ),
        ),
        const SizedBox(height: 7),
        TextFormField(
          controller: _toController,
          decoration: const InputDecoration(
            labelText: 'To',
            border: OutlineInputBorder(),
            suffixIcon: Icon(Icons.flight_land),
            filled: true,
          ),
        ),
        const SizedBox(height: 7),
        TextFormField(
          decoration: InputDecoration(
            labelText: 'Pick Date Range',
            suffixIcon: Icon(Icons.calendar_today),
            border: OutlineInputBorder(borderRadius: BorderRadius.circular(8)),
            filled: true,
            fillColor: Colors.grey[200],
          ),
          readOnly: true,
          controller: _dateRangeController,
          onTap: () => _selectDateRange(context),
        ),
        const SizedBox(height: 14),
        Padding(
          padding: const EdgeInsets.only(
            top: 10, // Regular padding on top
            left: 16, // Left padding
            right: 16, // Right padding
            bottom: 5, // Double padding on bottom
          ),
          child: ElevatedButton(
            onPressed: () {
              // Define what happens when the button is pressed, such as navigating to a new page or performing a search
            },
            child: const Text('Find your flight'),
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.green, // Background color of the button
              minimumSize: const Size(double.infinity, 50), // Ensures the button stretches to fill the horizontal space
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20), // Rounded corners
              ),
            ),
          ),
        ),
      ],
    );
  }

  Future<void> _selectDateRange(BuildContext context) async {
    final DateTimeRange? picked = await showDateRangePicker(
      context: context,
      firstDate: DateTime.now(),
      lastDate: DateTime(2100),
      initialDateRange: DateTimeRange(
        start: _startDate ?? DateTime.now(),
        end: (_endDate ?? DateTime.now()).add(const Duration(days: 7)),
      ),
    );
    if (picked != null && (picked.start != _startDate || picked.end != _endDate)) {
      setState(() {
        _startDate = picked.start;
        _endDate = picked.end;
        _dateRangeController.text = 'From: ${DateFormat('yyyy-MM-dd').format(_startDate!)} To: ${DateFormat('yyyy-MM-dd').format(_endDate!)}';
      });
    }
  }
}

class FlightResults extends StatefulWidget {
  const FlightResults({Key? key}) : super(key: key);

  @override
  _FlightResultsState createState() => _FlightResultsState();
}

class _FlightResultsState extends State<FlightResults> {
  late Future<List<Flight>> _futureFlights;
  final FlightService _flightService = FlightService();

  @override
  void initState() {
    super.initState();
    _futureFlights = _flightService.fetchFlights();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<Flight>>(
      future: _futureFlights,
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Center(child: CircularProgressIndicator());
        } else if (snapshot.hasError) {
          return Center(child: Text('Error: ${snapshot.error}'));
        } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
          return const Center(child: Text('No flights available'));
        } else {
          List<Flight> flights = snapshot.data!;
          return ListView.builder(
            itemCount: flights.length,
            itemBuilder: (context, index) {
              Flight flight = flights[index];
              return Card(
                margin: const EdgeInsets.all(8),
                elevation: 4,
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        children: [
                          Icon(Icons.airplanemode_active, size: 48, color: Theme.of(context).primaryColor),
                          const SizedBox(width: 16),
                          Expanded(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(flight.origin.nom, style: Theme.of(context).textTheme.titleMedium),
                                Text('${flight.heureDepart} - ${flight.heureArrivee}', style: Theme.of(context).textTheme.bodySmall),
                              ],
                            ),
                          ),
                          Text('\$${flight.prix}', style: Theme.of(context).textTheme.titleLarge),
                        ],
                      ),
                      const SizedBox(height: 8),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(flight.origin.nom, style: Theme.of(context).textTheme.titleLarge),
                            ],
                          ),
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.end,
                            children: [
                              Text(flight.destination.nom, style: Theme.of(context).textTheme.titleLarge),
                              const Icon(Icons.arrow_forward, size: 24),
                            ],
                          ),
                        ],
                      ),
                      const SizedBox(height: 8),
                      ElevatedButton(
                        onPressed: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => FlightDetailsScreen(flight: flight),
                            ),
                          );
                        },
                        style: ElevatedButton.styleFrom(
                          foregroundColor: Colors.white, backgroundColor: Colors.green, // Text color
                        ),
                        child: const Text('Book now'),
                      ),
                    ],
                  ),
                ),
              );
            },
          );
        }
      },
    );
  }
}
