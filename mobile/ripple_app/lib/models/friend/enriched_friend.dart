class EnrichedFriend {
  final int userId;
  final String username;
  final String email;

  EnrichedFriend({
    required this.userId,
    required this.username,
    required this.email,
  });

  factory EnrichedFriend.fromJson(Map<String, dynamic> json) {
    return EnrichedFriend(
      userId: json['userId'],
      username: json['username'],
      email: json['email'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'userId': userId,
      'username': username,
      'email': email,
    };
  }
}
