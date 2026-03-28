class FriendCount {
  final int followingCount;
  final int followerCount;

  FriendCount({required this.followingCount, required this.followerCount});

  factory FriendCount.fromJson(Map<String, dynamic> json) {
    return FriendCount(
        followingCount: json['followingCount'],
        followerCount: json['followerCount']);
  }

  Map<String, dynamic> toJson() {
    return {'followingCount': followingCount, 'followerCount': followerCount};
  }
}
