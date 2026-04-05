import 'package:flutter/material.dart';
import 'package:ripple_app/models/friend/enriched_friend.dart';
import 'package:ripple_app/service/friend_service.dart';

class FollowersScreen extends StatefulWidget {
  final int userId;
  const FollowersScreen({super.key, required this.userId});

  @override
  State<FollowersScreen> createState() => _FollowersScreenState();
}

class _FollowersScreenState extends State<FollowersScreen> {
  final FriendService _friendService = FriendService();
  List<EnrichedFriend> _followers = [];
  bool _isLoading = true;
  bool _hasError = false;

  @override
  void initState() {
    super.initState();
    _loadFollowers();
  }

  Future<void> _loadFollowers() async {
    final response =
        await _friendService.getEnrichedFollowers(userId: widget.userId);
    setState(() {
      if (response.isSuccess && response.data != null) {
        _followers = response.data!;
        _hasError = false;
      } else {
        _hasError = true;
      }
      _isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Followers')),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _hasError
              ? const Center(child: Text('Failed to load followers'))
              : _followers.isEmpty
                  ? const Center(child: Text('No followers yet'))
                  : ListView.builder(
                      itemCount: _followers.length,
                      itemBuilder: (context, index) {
                        final follower = _followers[index];
                        return ListTile(
                          leading: CircleAvatar(
                            child: Text(follower.username[0].toUpperCase()),
                          ),
                          title: Text(follower.username),
                          subtitle: Text(follower.email),
                          trailing: OutlinedButton(
                            onPressed: () {},
                            child: const Text('Follow'),
                          ),
                        );
                      },
                    ),
    );
  }
}
