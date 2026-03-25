package com.ripple.friend_service.service;


import com.ripple.friend_service.dto.FriendResponseDTO;
import com.ripple.friend_service.entity.Friend;

import java.util.List;

public interface FriendService {
    FriendResponseDTO follow(Long followerId, Long followingId );
    void unfollow(Long followerId,Long followingId);
    List<FriendResponseDTO> getAllFollowers(Long userId);
    List<FriendResponseDTO> getAllFollowing(Long userId);
}
