package com.ripple.friend_service.dto;

import lombok.Data;

@Data
public class FriendshipCountResponseDTO {
    private Long followingCount;
    private Long followerCount;
    private Boolean isFollowing;
}
