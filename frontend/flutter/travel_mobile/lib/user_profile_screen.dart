import 'dart:io';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:travel_mobile/models/user.dart';
import 'package:travel_mobile/services/user_service.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ProfileScreen extends StatefulWidget {
  @override
  _ProfileScreenState createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  late TextEditingController _nameController;
  late TextEditingController _emailController;
  late TextEditingController _phoneController;
  late TextEditingController _ageController;
  late TextEditingController _addressController;
  late TextEditingController _currentPasswordController;
  late TextEditingController _newPasswordController;

  final ImagePicker _picker = ImagePicker();
  late UserService _userService;
  User? _user;
  bool _isLoading = true;
  String? _email;
  XFile? _pickedImageFile;

  @override
  void initState() {
    super.initState();
    _userService = UserService();
    _loadEmail();
  }

  Future<void> _loadEmail() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      _email = prefs.getString('email');
    });
    if (_email != null) {
      _fetchUser();
    }
  }

  Future<void> _fetchUser() async {
    User? user = await _userService.fetchUserByEmail(_email!);
    if (user != null) {
      setState(() {
        _user = user;
        _nameController = TextEditingController(text: user.name);
        _emailController = TextEditingController(text: user.email);
        _phoneController = TextEditingController(text: user.phone);
        _ageController = TextEditingController(text: user.age.toString());
        _addressController = TextEditingController(text: user.address);
        _currentPasswordController = TextEditingController();
        _newPasswordController = TextEditingController();
        _isLoading = false;
      });
    } else {
      setState(() {
        _user = null;
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    if (_isLoading) {
      return Scaffold(
        appBar: AppBar(title: const Text("User Profile")),
        body: const Center(child: CircularProgressIndicator()),
      );
    }

    if (_user == null) {
      return Scaffold(
        appBar: AppBar(title: const Text("User Profile")),
        body: const Center(child: Text('Failed to load user')),
      );
    }

    return Scaffold(
      appBar: AppBar(
        title: const Text("User Profile"),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: _confirmLogout,
          ),
        ],
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          GestureDetector(
            onTap: _updateImage,
            child: CircleAvatar(
              radius: 60,
              backgroundImage: _pickedImageFile != null
                  ? FileImage(File(_pickedImageFile!.path))
                  : NetworkImage('http://192.168.152.1:8080${_user!.imagePath}') as ImageProvider,
              child: const Icon(Icons.add_a_photo, color: Colors.white70),
            ),
          ),
          const SizedBox(height: 16),
          _buildTextField(_nameController, "Name", Icons.person),
          _buildTextField(_emailController, "Email", Icons.email),
          _buildTextField(_phoneController, "Phone", Icons.phone),
          _buildTextField(_ageController, "Age", Icons.cake),
          _buildTextField(_addressController, "Address", Icons.home),
          _buildTextField(_currentPasswordController, "Current Password", Icons.lock_outline, isPassword: true),
          _buildTextField(_newPasswordController, "New Password", Icons.lock, isPassword: true),
          const SizedBox(height: 20),
          ElevatedButton(
            onPressed: _updateProfile,
            child: const Text('Update Profile'),
            style: ElevatedButton.styleFrom(
              minimumSize: const Size(double.infinity, 50),
              backgroundColor: const Color(0xffffc14d),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildTextField(TextEditingController controller, String label, IconData icon, {bool isPassword = false}) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8),
      child: TextField(
        controller: controller,
        obscureText: isPassword,
        decoration: InputDecoration(
          labelText: label,
          prefixIcon: Icon(icon),
          border: const OutlineInputBorder(),
        ),
      ),
    );
  }

  void _updateProfile() async {
  if (_user != null) {
    // If the new password field is empty, use the current password
    String passwordToUpdate = _newPasswordController.text.isNotEmpty
        ? _newPasswordController.text
        : _currentPasswordController.text;

    String? message = await _userService.updateUser(
      User(
        id: _user!.id,
        name: _nameController.text,
        email: _emailController.text,
        phone: _phoneController.text,
        age: int.parse(_ageController.text),
        address: _addressController.text,
        password: _user!.password,
        imagePath: _user!.imagePath, // Ensure this remains the same unless changed
      ),
      _currentPasswordController.text,
      newPassword: passwordToUpdate,
      imagePath: _pickedImageFile?.path,
    );
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(message ?? 'Error updating profile')));
  }
}


  void _confirmLogout() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Confirm Logout'),
          content: const Text('Are you sure you want to logout?'),
          actions: <Widget>[
            TextButton(
              child: const Text('Cancel'),
              onPressed: () => Navigator.of(context).pop(),
            ),
            TextButton(
              child: const Text('Logout'),
              onPressed: () {
                Navigator.of(context).pop();
                _logout();
              },
            ),
          ],
        );
      },
    );
  }

  void _logout() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.remove('email');
    Navigator.of(context).pushReplacementNamed('/login'); // Assuming you have a login route
  }

  void _updateImage() async {
    final XFile? pickedFile = await _picker.pickImage(source: ImageSource.gallery);
    if (pickedFile != null) {
      setState(() {
        _pickedImageFile = pickedFile;
      });
    }
  }
}
