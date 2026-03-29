import 'package:flutter/material.dart';
import 'package:ripple_app/service/friend_service.dart';

class ProfileScreen extends StatefulWidget {
  final int userId;
  const ProfileScreen({super.key, required this.userId});
  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  int followerCount = 0;
  int followingCount = 0;
  bool isFollowing = false;
  bool isLoading = false;
  bool isLoadingCounts = true;
  FriendService friendService = FriendService();

  @override
  void initState() {
    super.initState();
    _loadCounts();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
          title: Text(
        'Profile',
      )),
      body: (isLoadingCounts)
          ? const Center(child: CircularProgressIndicator())
          : SafeArea(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const SizedBox(height: 5),
                  Text('FollowerCount: $followerCount'),
                  const SizedBox(height: 5),
                  Text('FollowingCount: $followingCount'),
                  const SizedBox(height: 5),
                  TextButton(
                    onPressed: isLoading
                        ? null
                        : () {
                            isFollowing ? _unFollow() : _follow();
                          },
                    child: isFollowing ? Text('UnFollow') : Text('Follow'),
                  )
                ],
              ),
            ),
    );
  }

  Future<void> _loadCounts() async {
    final response = await friendService.getCount(userId: widget.userId);
    if (response.isSuccess && response.data != null) {
      setState(() {
        followerCount = response.data!.followerCount;
        followingCount = response.data!.followingCount;
        isFollowing = response.data!.isFollowing;
        isLoadingCounts = false;
      });
    } else {
      isLoadingCounts = false;
    }
  }

  Future<void> _unFollow() async {
    setState(() {
      isFollowing = false;
      followerCount--;
      isLoading = true;
    });
    final response = await friendService.unfollow(followingId: widget.userId);
    if (response.isSuccess) {
      setState(() {
        isLoading = false;
      });
    } else {
      setState(() {
        isFollowing = true;
        followerCount++;
        isLoading = false;
      });
    }
  }

  Future<void> _follow() async {
    setState(() {
      isFollowing = true;
      followerCount++;
      isLoading = true;
    });
    final response = await friendService.follow(followingId: widget.userId);
    if (response.isSuccess) {
      setState(() {
        isLoading = false;
      });
    } else {
      setState(() {
        isFollowing = false;
        followerCount--;
        isLoading = false;
      });
    }
  }
}
