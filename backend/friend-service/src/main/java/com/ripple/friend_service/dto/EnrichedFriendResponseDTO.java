package com.ripple.friend_service.dto;

import lombok.Data;

@Data
public class EnrichedFriendResponseDTO {
    private Long userId;
    private String username;
    private String email;
}
