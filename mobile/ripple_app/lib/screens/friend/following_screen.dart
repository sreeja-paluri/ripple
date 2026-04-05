import 'package:flutter/material.dart';
import 'package:ripple_app/models/friend/enriched_friend.dart';
import 'package:ripple_app/service/friend_service.dart';

class FollowingScreen extends StatefulWidget {
  final int userId;
  const FollowingScreen({super.key, required this.userId});

  @override
  State<FollowingScreen> createState() => _FollowingScreenState();
}

class _FollowingScreenState extends State<FollowingScreen> {
  final FriendService _friendService = FriendService();
  List<EnrichedFriend> _following = [];
  bool _isLoading = true;
  bool _hasError = false;

  @override
  void initState() {
    super.initState();
    _loadFollowing();
  }

  Future<void> _loadFollowing() async {
    final response =
        await _friendService.getEnrichedFollowing(userId: widget.userId);
    setState(() {
      if (response.isSuccess && response.data != null) {
        _following = response.data!;
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
      appBar: AppBar(title: const Text('Following')),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _hasError
              ? const Center(child: Text('Failed to load following'))
              : _following.isEmpty
                  ? const Center(child: Text('Not following anyone yet'))
                  : ListView.builder(
                      itemCount: _following.length,
                      itemBuilder: (context, index) {
                        final user = _following[index];
                        return ListTile(
                          leading: CircleAvatar(
                            child: Text(user.username[0].toUpperCase()),
                          ),
                          title: Text(user.username),
                          subtitle: Text(user.email),
                          trailing: OutlinedButton(
                            onPressed: () {},
                            child: const Text('UnFollow'),
                          ),
                        );
                      },
                    ),
    );
  }
}
