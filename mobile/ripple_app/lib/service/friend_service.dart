import 'dart:convert';

import 'package:ripple_app/models/api_response.dart';
import 'package:ripple_app/models/friend/friend.dart';
import 'package:ripple_app/models/friend/friend_count.dart';
import 'package:ripple_app/models/user.dart';
import 'package:ripple_app/service/api_client.dart';
import 'package:ripple_app/utils/app_constants.dart';

class FriendService {
  final _client = ApiClient.instance;
  final baseUrl = AppConstants.friendServiceUrl;
  //follow
  Future<ApiResponse<Friend>> follow({required int followingId}) async {
    final body = <String, dynamic>{'followingId': followingId};
    final raw = await _client.post("/api/friends/follow", body,
        baseUrl: baseUrl, includeUserId: true);
    return ApiResponse.fromJson(raw, (data) => Friend.fromJson(data));
  }

  //unfollow
  Future<ApiResponse<dynamic>> unfollow({required int followingId}) async {
    final raw = await _client.delete("/api/friends/unfollow/$followingId",
        baseUrl: baseUrl, includeUserId: true);
    return ApiResponse.fromJson(raw, (_) => null);
  }

  //getCount
  Future<ApiResponse<FriendCount>> getCount({required int userId}) async {
    final raw =
        await _client.get("/api/friends/counts/$userId", baseUrl: baseUrl);
    return ApiResponse.fromJson(raw, (data) => FriendCount.fromJson(data));
  }

  //getFollowers
  Future<ApiResponse<List<Friend>>> getFollowers({required int userId}) async {
    final raw =
        await _client.get("/api/friends/followers/$userId", baseUrl: baseUrl);
    final response = ApiResponse.fromJson(raw,
        (data) => (data as List).map((item) => Friend.fromJson(item)).toList());

    return response;
  }

  //getFollowing
  Future<ApiResponse<List<Friend>>> getFollowing({required int userId}) async {
    final raw =
        await _client.get("/api/friends/following/$userId", baseUrl: baseUrl);
    return ApiResponse.fromJson(raw,
        (data) => (data as List).map((item) => Friend.fromJson(item)).toList());
  }
}
