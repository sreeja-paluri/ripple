class FriendCount {
  final int followerId;
  final int followingId;

  FriendCount({required this.followerId, required this.followingId});

  factory FriendCount.fromJson(Map<String, dynamic> json) {
    return FriendCount(
        followerId: json['followerId'], followingId: json['followingId']);
  }

  Map<String, dynamic> toJson() {
    return {'followingId': followingId, 'followerId': followerId};
  }
}
