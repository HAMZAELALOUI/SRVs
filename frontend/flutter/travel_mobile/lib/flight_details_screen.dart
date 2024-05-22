import 'package:flutter/material.dart';
import 'package:travel_mobile/seat_selection.dart';
import 'models/flight.dart';


class FlightDetailsScreen extends StatelessWidget {
  final Flight flight;

  const FlightDetailsScreen({super.key, required this.flight});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Flight Details'),
      ),
      body: Padding(
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
                    builder: (context) => SeatSelectionScreen(flight: flight),
                  ),
                );
              },
              style: ElevatedButton.styleFrom(
                foregroundColor: Colors.white,
                backgroundColor: Colors.green, // Text color
              ),
              child: const Text('Confirm Booking'),
            ),
          ],
        ),
      ),
    );
  }
}
