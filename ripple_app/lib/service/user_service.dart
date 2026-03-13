import 'package:ripple_app/models/api_response.dart';
import 'package:ripple_app/models/user.dart';

import 'api_client.dart';

class UserService {
  final _client = ApiClient.instance;

  Future<ApiResponse<List<User>>> getUsers(
      {int page = 0, int size = 10}) async {
    final raw = await _client.get('/api/users?page=$page&size=$size');

    // Backend returns Page<UserDTO.Response> — extract content list
    return ApiResponse<List<User>>(
      success: raw['success'] ?? false,
      message: raw['message'] ?? '',
      data: raw['data'] != null
          ? (raw['data']['content'] as List<dynamic>)
              .map((e) => User.fromJson(e))
              .toList()
          : null,
    );
  }

  Future<ApiResponse<User>> getUserById(int id) async {
    final raw = await _client.get('/api/users/$id');
    return ApiResponse.fromJson(raw, (data) => User.fromJson(data));
  }

  Future<ApiResponse<User>> createUser({
    required String username,
    required String email,
    required String password,
  }) async {
    final raw = await _client.post(
      '/api/users',
      {'username': username, 'email': email, 'password': password},
      requiresAuth: true,
    );
    return ApiResponse.fromJson(raw, (data) => User.fromJson(data));
  }

  Future<ApiResponse<User>> updateUser(
    int id, {
    required String username,
    required String email,
    String? password,
  }) async {
    final body = <String, dynamic>{
      'username': username,
      'email': email,
      if (password != null && password.isNotEmpty) 'password': password,
    };
    final raw = await _client.put('/api/users/$id', body);
    return ApiResponse.fromJson(raw, (data) => User.fromJson(data));
  }

  Future<ApiResponse<void>> deleteUser(int id) async {
    final raw = await _client.delete('/api/users/$id');
    return ApiResponse.fromJson(raw, (_) {});
  }
}
