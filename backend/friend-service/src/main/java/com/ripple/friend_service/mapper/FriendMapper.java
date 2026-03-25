package com.ripple.friend_service.mapper;

import com.ripple.friend_service.dto.FriendRequestDTO;
import com.ripple.friend_service.dto.FriendResponseDTO;
import com.ripple.friend_service.entity.Friend;
import org.springframework.stereotype.Component;

@Component
public class FriendMapper {

    public Friend toEntity(FriendRequestDTO request){
        Friend friend = new Friend();
        friend.setFollowingId(request.getFollowingId());
        return friend;
    }

    public FriendResponseDTO toResponse(Friend friend){
        FriendResponseDTO response = new FriendResponseDTO();
        response.setId(friend.getId());
        response.setFollowerId(friend.getFollowerId());
        response.setFollowingId(friend.getFollowingId());
        response.setCreatedAt(friend.getCreatedAt());
        return response;
    }
}
