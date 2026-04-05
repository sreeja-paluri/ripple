import 'package:flutter/material.dart';
import 'package:ripple_app/screens/friend/followers_screen.dart';
import 'package:ripple_app/screens/friend/following_screen.dart';
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
          : Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  GestureDetector(
                    onTap: () => Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (_) => FollowersScreen(userId: widget.userId),
                      ),
                    ),
                    child: Text('Followers: $followerCount'),
                  ),
                  const SizedBox(height: 8),
                  GestureDetector(
                    onTap: () => Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (_) => FollowingScreen(userId: widget.userId),
                      ),
                    ),
                    child: Text('Following: $followingCount'),
                  ),
                  const SizedBox(height: 8),
                  TextButton(
                    onPressed: isLoading
                        ? null
                        : () {
                            isFollowing ? _unFollow() : _follow();
                          },
                    child: isFollowing
                        ? const Text('UnFollow')
                        : const Text('Follow'),
                  ),
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
