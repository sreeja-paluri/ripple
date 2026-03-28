class FriendCount {
  final int followingCount;
  final int followerCount;
  final bool isFollowing;

  FriendCount(
      {required this.followingCount,
      required this.followerCount,
      required this.isFollowing});

  factory FriendCount.fromJson(Map<String, dynamic> json) {
    return FriendCount(
        followingCount: json['followingCount'],
        followerCount: json['followerCount'],
        isFollowing: json['isFollowing']);
  }

  Map<String, dynamic> toJson() {
    return {
      'followingCount': followingCount,
      'followerCount': followerCount,
      'isFollowing': isFollowing
    };
  }
}
