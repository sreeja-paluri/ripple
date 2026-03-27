class Friend {
  //1. field
  final int id;
  final int followingId;
  final int followerId;
  final String createdAt;

//constructor
  const Friend(
      {required this.id,
      required this.followingId,
      required this.followerId,
      required this.createdAt});

//fromJson
  factory Friend.fromJson(Map<String, dynamic> json) {
    return Friend(
        id: json['id'],
        followingId: json['followingId'],
        followerId: json['followerId'],
        createdAt: json['createdAt']);
  }

//toJson
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'followerId': followerId,
      'followingId': followingId,
      'createdAt': createdAt
    };
  }
}
