import 'dart:io';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:travel_mobile/services/auth_service.dart';
import 'package:travel_mobile/LoginPage.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  final ImagePicker _picker = ImagePicker();
  final AuthService _authService = AuthService();
  XFile? _profileImage;

  // Form data
  String? name, email, phone, password, confirmPassword, address;
  int? age;

  Future<void> _pickImage() async {
    final XFile? image = await _picker.pickImage(source: ImageSource.gallery);
    if (image != null) {
      setState(() {
        _profileImage = image;
      });
    }
  }

  void _submitForm() async {
    if (_formKey.currentState!.validate()) {
      _formKey.currentState!.save();
      String? response = await _authService.register(
        name: name!,
        email: email!,
        phone: phone!,
        age: age!,
        address: address!,
        password: password!,
        profilePicture: _profileImage != null ? File(_profileImage!.path) : null,
      );

      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(response!)));

      if (response == 'User registered successfully.') {
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => const LoginPage()),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sign Up'),
        backgroundColor: Color(0xffffc14d),
      ),
      body: SingleChildScrollView(
        child: Form(
          key: _formKey,
          child: Padding(
            padding: const EdgeInsets.all(20.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                Center(
                  child: GestureDetector(
                    onTap: _pickImage,
                    child: CircleAvatar(
                      radius: 60,
                      backgroundColor: Colors.grey[200],
                      child: _profileImage != null
                          ? ClipOval(child: Image.file(File(_profileImage!.path), fit: BoxFit.cover, width: 120, height: 120))
                          : const Icon(Icons.camera_alt, color: Color(0xffffc14d), size: 50),
                    ),
                  ),
                ),
                const SizedBox(height: 20),
                buildTextFormField('Name', Icons.person, false),
                const SizedBox(height: 10),
                buildTextFormField('Email', Icons.email, false),
                const SizedBox(height: 10),
                buildTextFormField('Phone', Icons.phone, false),
                const SizedBox(height: 10),
                buildTextFormField('Age', Icons.cake, false),
                const SizedBox(height: 10),
                buildTextFormField('Password', Icons.lock, true),
                const SizedBox(height: 10),
                buildTextFormField('Confirm Password', Icons.lock_outline, true),
                const SizedBox(height: 10),
                buildTextFormField('Address', Icons.home, false),
                const SizedBox(height: 20),
                ElevatedButton(
                  onPressed: _submitForm,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Color(0xffffc14d),
                    minimumSize: const Size(double.infinity, 50),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(20),
                    ),
                  ),
                  child: const Text(
                    'Sign Up',
                    style: TextStyle(color: Colors.white),
                  ),
                ),
                const SizedBox(height: 20),
                TextButton(
                  onPressed: () {
                    Navigator.pop(context);  // Assuming the login page is just one step back in the navigation stack
                  },
                  child: const Text(
                    'Already have an account? Log in',
                    style: TextStyle(color: Color(0xffffc14d)),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  TextFormField buildTextFormField(String label, IconData icon, bool obscureText) {
    return TextFormField(
      decoration: InputDecoration(
        labelText: label,
        prefixIcon: Icon(icon, color: Color(0xffffc14d)),
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(20)),
        filled: true,
        fillColor: Colors.grey[200],
      ),
      obscureText: obscureText,
      validator: (value) {
        if (value!.isEmpty) return 'Please enter your $label';
        if (label == 'Confirm Password' && value != password) return 'Passwords do not match';
        return null;
      },
      onChanged: (value) {
        if (label == 'Password') password = value;
        if (label == 'Confirm Password') confirmPassword = value;
      },
      onSaved: (value) {
        switch (label) {
          case 'Name': name = value; break;
          case 'Email': email = value; break;
          case 'Phone': phone = value; break;
          case 'Age': age = int.tryParse(value!); break;
          case 'Password': password = value; break;
          case 'Address': address = value; break;
        }
      },
    );
  }
}
