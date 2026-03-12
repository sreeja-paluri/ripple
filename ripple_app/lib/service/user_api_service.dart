import 'dart:convert';
import 'package:http/http.dart' as http;

class UserApiService {
  final String baseUrl = "http://localhost:8080";

  Future<void> createUser(
      String username, String email, String password) async {
    print("API CALL STARTED");

    try {
      final response = await http.post(
        Uri.parse('$baseUrl/api/users'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: jsonEncode({
          'username': username,
          'email': email,
          'password': password,
        }),
      );

      print("STATUS: ${response.statusCode}");
      print("BODY: ${response.body}");
    } catch (e) {
      print("NETWORK ERROR: $e");
    }
  }
}
