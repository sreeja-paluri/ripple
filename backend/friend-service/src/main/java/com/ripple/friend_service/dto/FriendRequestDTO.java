package com.ripple.friend_service.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FriendRequestDTO {
    @NotNull
    private Long followingId;
}
