import 'package:flutter/material.dart';
import 'package:travel_mobile/HomePage.dart';
import 'package:travel_mobile/RegisterPage.dart';
import 'package:travel_mobile/SplashScreen.dart';
import 'package:travel_mobile/LoginPage.dart';
import 'package:travel_mobile/user_profile_screen.dart';
import 'package:travel_mobile/models/user.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Travel App',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(
          seedColor: Colors.orange,
          brightness: Brightness.light,
        ),
        useMaterial3: true,
      ),
      initialRoute: '/',
      onGenerateRoute: (settings) {
        if (settings.name == '/home') {
          final User user = settings.arguments as User;
          return MaterialPageRoute(
            builder: (context) => HomePage(user: user),
          );
        } else if (settings.name == '/profile') {
          return MaterialPageRoute(
            builder: (context) => ProfileScreen(),
          );
        }
        // Add more routes as necessary
        return null;
      },
      routes: {
        '/': (context) => const SplashScreen(),
        '/register': (context) => const RegisterPage(),
        '/login': (context) => const LoginPage(),
      },
    );
  }
}
