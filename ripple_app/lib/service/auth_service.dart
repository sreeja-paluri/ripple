import 'package:ripple_app/models/api_response.dart';
import 'package:ripple_app/models/token_response.dart';
import 'package:ripple_app/models/user.dart';

import 'api_client.dart';

class AuthService {
  final _client = ApiClient.instance;

  Future<ApiResponse<User>> register({
    required String username,
    required String email,
    required String password,
  }) async {
    final raw = await _client.post(
      '/api/auth/register',
      {'username': username, 'email': email, 'password': password},
    );
    return ApiResponse.fromJson(raw, (data) => User.fromJson(data));
  }

  Future<ApiResponse<TokenResponse>> login({
    required String email,
    required String password,
  }) async {
    final raw = await _client.post(
      '/api/auth/login',
      {'email': email, 'password': password},
    );

    final response = ApiResponse.fromJson(
      raw,
      (data) => TokenResponse.fromJson(data),
    );

    // Save token on successful login
    if (response.isSuccess && response.data?.token != null) {
      await _client.saveToken(response.data!.token);
    }

    return response;
  }

  Future<void> logout() async {
    await _client.clearToken();
  }

  Future<bool> isLoggedIn() async {
    final token = await _client.getToken();
    return token != null && token.isNotEmpty;
  }
}
