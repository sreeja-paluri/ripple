import 'dart:async';
import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:ripple_app/utils/app_constants.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ApiClient {
  ApiClient._();
  static final ApiClient instance = ApiClient._();

  // ─── Token helpers ────────────────────────────────────────────────────────

  Future<String?> getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(AppConstants.tokenKey);
  }

  Future<void> saveToken(String token) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(AppConstants.tokenKey, token);
  }

  Future<void> clearToken() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(AppConstants.tokenKey);
  }

  // ─── User Info helpers ────────────────────────────────────────────────────────

  Future<void> saveUserId(String userId) async {
    final pref = await SharedPreferences.getInstance();
    await pref.setString(AppConstants.userIdKey, userId);
  }

  Future<String?> getUserId() async {
    final pref = await SharedPreferences.getInstance();
    return pref.getString(AppConstants.userIdKey);
  }

  Future<void> clearUserId() async {
    final pref = await SharedPreferences.getInstance();
    pref.remove(AppConstants.userIdKey);
  }

  // ─── Auth headers ─────────────────────────────────────────────────────────

  Future<Map<String, String>> _headers(
      {bool requiresAuth = true, bool includeUserId = false}) async {
    final headers = <String, String>{
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    };

    if (requiresAuth) {
      final token = await getToken();
      if (token != null) {
        headers['Authorization'] = 'Bearer $token';
      }
    }

    if (includeUserId) {
      final userId = await getUserId();
      if (userId != null) {
        headers["X-User-Id"] = userId;
      }
    }

    return headers;
  }

  // ─── HTTP methods ─────────────────────────────────────────────────────────

  Future<Map<String, dynamic>> get(
    String path, {
    bool requiresAuth = true,
    bool includeUserId = false,
    String? baseUrl,
  }) async {
    return _execute(() async => http.get(
          Uri.parse('${baseUrl ?? AppConstants.userServiceUrl}$path'),
          headers: await _headers(
              requiresAuth: requiresAuth, includeUserId: includeUserId),
        ));
  }

  Future<Map<String, dynamic>> post(
    String path,
    Map<String, dynamic> body, {
    bool requiresAuth = false,
    bool includeUserId = false,
    String? baseUrl,
  }) async {
    return _execute(() async => http.post(
          Uri.parse('${baseUrl ?? AppConstants.userServiceUrl}$path'),
          headers: await _headers(
              requiresAuth: requiresAuth, includeUserId: includeUserId),
          body: jsonEncode(body),
        ));
  }

  Future<Map<String, dynamic>> put(
    String path,
    Map<String, dynamic> body, {
    bool requiresAuth = true,
    bool includeUserId = false,
    String? baseUrl,
  }) async {
    return _execute(() async => http.put(
          Uri.parse('${baseUrl ?? AppConstants.userServiceUrl}$path'),
          headers: await _headers(
              requiresAuth: requiresAuth, includeUserId: includeUserId),
          body: jsonEncode(body),
        ));
  }

  Future<Map<String, dynamic>> delete(
    String path, {
    bool requiresAuth = true,
    bool includeUserId = false,
    String? baseUrl,
  }) async {
    return _execute(() async => http.delete(
          Uri.parse('${baseUrl ?? AppConstants.userServiceUrl}$path'),
          headers: await _headers(
              requiresAuth: requiresAuth, includeUserId: includeUserId),
        ));
  }

  // ─── Core executor ────────────────────────────────────────────────────────

  Future<Map<String, dynamic>> _execute(
    Future<http.Response> Function() requestFn,
  ) async {
    try {
      final response = await requestFn().timeout(AppConstants.requestTimeout);
      return jsonDecode(response.body) as Map<String, dynamic>;
    } on SocketException {
      return _errorMap('No internet connection or server unreachable');
    } on TimeoutException {
      return _errorMap('Request timed out. Please try again.');
    } on HttpException {
      return _errorMap('Server error. Please try again.');
    } on FormatException {
      return _errorMap('Unexpected response format from server.');
    } catch (e) {
      return _errorMap('Something went wrong: $e');
    }
  }

  /// Builds a backend-shaped error map so callers always get a consistent envelope.
  Map<String, dynamic> _errorMap(String message) => {
        'status': 'error',
        'success': false, // ← add this
        'message': message,
        'data': null,
      };
}
