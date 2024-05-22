import 'dart:io';
import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:path_provider/path_provider.dart';
import 'package:pdf/pdf.dart';
import 'package:pdf/widgets.dart' as pw;
import 'package:qr_flutter/qr_flutter.dart';
import 'package:open_file/open_file.dart';
import 'models/flight.dart';

class SeatSelectionScreen extends StatefulWidget {
  final Flight flight;

  const SeatSelectionScreen({super.key, required this.flight});

  @override
  _SeatSelectionScreenState createState() => _SeatSelectionScreenState();
}

class _SeatSelectionScreenState extends State<SeatSelectionScreen> {
  final int rows = 10;
  final int cols = 6;
  List<List<SeatState>> seatLayout = [];

  @override
  void initState() {
    super.initState();
    seatLayout = List.generate(
      rows,
      (row) => List.generate(cols, (col) => SeatState.available),
    );
  }

  String? selectedSeat;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Select Your Seat'),
        backgroundColor: Colors.white,
        iconTheme: const IconThemeData(color: Colors.black),
        titleTextStyle: const TextStyle(color: Colors.black),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Select a seat for your flight from ${widget.flight.origin.nom} to ${widget.flight.destination.nom}',
              style: Theme.of(context).textTheme.bodyMedium,
            ),
            const SizedBox(height: 16),
            Expanded(
              child: Center(
                child: AspectRatio(
                  aspectRatio: 2 / 3,
                  child: Container(
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(20),
                      border: Border.all(color: Colors.grey),
                      color: Colors.white,
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black12,
                          spreadRadius: 2,
                          blurRadius: 5,
                        ),
                      ],
                    ),
                    padding: const EdgeInsets.all(16.0),
                    child: Column(
                      children: [
                        Expanded(
                          child: GridView.builder(
                            gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                              crossAxisCount: 7, // 6 seats + 1 for aisle
                              mainAxisSpacing: 8,
                              crossAxisSpacing: 8,
                              childAspectRatio: 1.5,
                            ),
                            itemCount: rows * 7,
                            itemBuilder: (context, index) {
                              final row = index ~/ 7;
                              final col = index % 7;

                              if (col == 3) {
                                return const SizedBox.shrink(); // Aisle space
                              }

                              final seat = '${String.fromCharCode(65 + row)}${col >= 3 ? col : col + 1}';
                              final seatState = seatLayout[row][col > 2 ? col - 1 : col];

                              return GestureDetector(
                                onTap: () {
                                  setState(() {
                                    if (seatState == SeatState.available) {
                                      for (var r = 0; r < rows; r++) {
                                        for (var c = 0; c < cols; c++) {
                                          if (seatLayout[r][c] == SeatState.selected) {
                                            seatLayout[r][c] = SeatState.available;
                                          }
                                        }
                                      }
                                      seatLayout[row][col > 2 ? col - 1 : col] = SeatState.selected;
                                      selectedSeat = seat;
                                    } else if (seatState == SeatState.selected) {
                                      seatLayout[row][col > 2 ? col - 1 : col] = SeatState.available;
                                      selectedSeat = null;
                                    }
                                  });
                                },
                                child: Container(
                                  decoration: BoxDecoration(
                                    color: _getSeatColor(seatState),
                                    borderRadius: BorderRadius.circular(8),
                                    boxShadow: [
                                      BoxShadow(
                                        color: Colors.black26,
                                        blurRadius: 4,
                                        offset: Offset(2, 2),
                                      ),
                                    ],
                                  ),
                                  child: Center(
                                    child: Text(
                                      seat,
                                      style: TextStyle(
                                        color: seatState == SeatState.selected ? Colors.white : Colors.black,
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                                  ),
                                ),
                              );
                            },
                          ),
                        ),
                        const SizedBox(height: 16),
                        ElevatedButton(
                          onPressed: selectedSeat != null
                              ? () {
                                  Navigator.pop(context);
                                  _showPaymentDialog(context);
                                }
                              : null,
                          style: ElevatedButton.styleFrom(
                            foregroundColor: Colors.white,
                            backgroundColor: selectedSeat != null ? Colors.green : Colors.grey,
                            padding: const EdgeInsets.symmetric(horizontal: 32, vertical: 16),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(20),
                            ),
                          ),
                          child: const Text('Proceed to Payment'),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Color _getSeatColor(SeatState seatState) {
    switch (seatState) {
      case SeatState.available:
        return Colors.grey[300]!;
      case SeatState.selected:
        return Colors.green;
      case SeatState.booked:
        return Colors.red;
      case SeatState.disabled:
        return Colors.grey;
      default:
        return Colors.grey[300]!;
    }
  }

  void _showPaymentDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
          title: const Text('Payment'),
          content: SingleChildScrollView(
            child: Column(
              children: [
                Text(
                  'Please enter your payment details to confirm the booking.',
                  style: Theme.of(context).textTheme.bodyMedium,
                ),
                const SizedBox(height: 16),
                TextField(
                  decoration: const InputDecoration(
                    labelText: 'Card Number',
                    border: OutlineInputBorder(),
                  ),
                  keyboardType: TextInputType.number,
                ),
                const SizedBox(height: 16),
                TextField(
                  decoration: const InputDecoration(
                    labelText: 'Card Holder Name',
                    border: OutlineInputBorder(),
                  ),
                ),
                const SizedBox(height: 16),
                Row(
                  children: [
                    Expanded(
                      child: TextField(
                        decoration: const InputDecoration(
                          labelText: 'Expiry Date',
                          border: OutlineInputBorder(),
                        ),
                        keyboardType: TextInputType.datetime,
                      ),
                    ),
                    const SizedBox(width: 16),
                    Expanded(
                      child: TextField(
                        decoration: const InputDecoration(
                          labelText: 'CVV',
                          border: OutlineInputBorder(),
                        ),
                        keyboardType: TextInputType.number,
                        obscureText: true,
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const Text('Cancel'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.green,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(20),
                ),
              ),
              child: const Text('Pay'),
              onPressed: () {
                Navigator.of(context).pop();
                _showConfirmationDialog(context);
              },
            ),
          ],
        );
      },
    );
  }

  void _showConfirmationDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
          title: const Text('Confirm Payment'),
          content: const Text('Do you want to confirm the payment?'),
          actions: <Widget>[
            TextButton(
              child: const Text('Cancel'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.green,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(20),
                ),
              ),
              child: const Text('Confirm'),
              onPressed: () {
                Navigator.of(context).pop();
                _showSuccessDialog(context);
              },
            ),
          ],
        );
      },
    );
  }

  void _showSuccessDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
          title: const Text('Success'),
          content: const Text('Your payment has been confirmed successfully!'),
          actions: <Widget>[
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.green,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(20),
                ),
              ),
              child: const Text('OK'),
              onPressed: () {
                Navigator.of(context).pop();
                _generateAndOpenPDF(context);
              },
            ),
          ],
        );
      },
    );
  }

  Future<void> _generateAndOpenPDF(BuildContext context) async {
    final pdf = pw.Document();

    // Generate QR code as an image
    final qrImage = await QrPainter(
      data: 'Flight: ${widget.flight.idVol}, Seat: $selectedSeat',
      version: QrVersions.auto,
      gapless: false,
    ).toImage(200);

    // Convert QR code to byte data
    final byteData = await qrImage.toByteData(format: ImageByteFormat.png);
    final qrBytes = byteData!.buffer.asUint8List();

    final tempDir = await getTemporaryDirectory();
    final filePath = '${tempDir.path}/flight_ticket.pdf';
    final file = File(filePath);

    pdf.addPage(
      pw.Page(
        build: (pw.Context context) {
          return pw.Container(
            padding: const pw.EdgeInsets.all(16),
            decoration: pw.BoxDecoration(
              border: pw.Border.all(),
              borderRadius: pw.BorderRadius.circular(20),
            ),
            child: pw.Column(
              crossAxisAlignment: pw.CrossAxisAlignment.start,
              children: [
                pw.Text(
                  'Flight Ticket',
                  style: pw.TextStyle(fontSize: 24, fontWeight: pw.FontWeight.bold),
                ),
                pw.SizedBox(height: 16),
                pw.Text('Flight: ${widget.flight.idVol}', style: pw.TextStyle(fontSize: 18)),
                pw.Text('From: ${widget.flight.origin.nom}', style: pw.TextStyle(fontSize: 18)),
                pw.Text('To: ${widget.flight.destination.nom}', style: pw.TextStyle(fontSize: 18)),
                pw.Text('Seat: $selectedSeat', style: pw.TextStyle(fontSize: 18)),
                pw.SizedBox(height: 16),
                pw.Container(
                  width: 200,
                  height: 200,
                  child: pw.Image(
                    pw.MemoryImage(qrBytes),
                  ),
                ),
                pw.SizedBox(height: 16),
                pw.Text('Have a nice flight!', style: pw.TextStyle(fontSize: 18, fontWeight: pw.FontWeight.bold)),
              ],
            ),
          );
        },
      ),
    );

    await file.writeAsBytes(await pdf.save());

    // Open the PDF file
    await OpenFile.open(filePath);
  }
}

enum SeatState { available, selected, booked, disabled }
