import 'user.dart';

class TokenResponse {
  final String token;
  final int expiresInMs;
  final String tokenType;
  final User? user;

  TokenResponse({
    required this.token,
    required this.expiresInMs,
    required this.tokenType,
    this.user,
  });

  factory TokenResponse.fromJson(Map<String, dynamic> json) {
    return TokenResponse(
      token: json['token'],
      expiresInMs: json['expiresInMs'],
      tokenType: json['tokenType'] ?? 'Bearer',
      user: json['user'] != null ? User.fromJson(json['user']) : null,
    );
  }
}
