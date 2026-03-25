package com.ripple.friend_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendResponseDTO {
    private Long id;
    private Long followerId;
    private Long followingId;
    private LocalDateTime createdAt;
}
