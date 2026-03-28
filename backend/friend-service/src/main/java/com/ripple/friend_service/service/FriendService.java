package com.ripple.friend_service.service;


import com.ripple.friend_service.dto.FriendResponseDTO;

import java.util.List;

public interface FriendService {
    FriendResponseDTO follow(Long followerId, Long followingId );
    void unfollow(Long followerId,Long followingId);
    List<FriendResponseDTO> getAllFollowers(Long userId);
    List<FriendResponseDTO> getAllFollowing(Long userId);

    Long getCountOfFollowers(Long userId);
    Long getCountOfFollowing(Long userId);
    Boolean isFollowing(Long userId, Long currentUserId);
}
