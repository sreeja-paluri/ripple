package com.ripple.friend_service.service.impl;

import com.ripple.friend_service.dto.FriendResponseDTO;
import com.ripple.friend_service.entity.Friend;
import com.ripple.friend_service.mapper.FriendMapper;
import com.ripple.friend_service.repository.FriendRepository;
import com.ripple.friend_service.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    public final FriendRepository friendRepository;
    public final FriendMapper mapper;
    @Override
    public FriendResponseDTO follow(Long followerId, Long followingId){
        if(followerId.equals(followingId)){
            throw new IllegalArgumentException("Can't follow yourself");
        }
        if(friendRepository.existsByFollowerIdAndFollowingId(followerId,followingId)){
            throw new IllegalArgumentException("Already following this user");
        }
        Friend friend = new Friend();
        friend.setFollowingId(followingId);
        friend.setFollowerId(followerId);
        Friend saved = friendRepository.save(friend);
        return mapper.toResponse(saved);
    }

    @Override
    public void unfollow(Long followerId, Long followingId) {
        if(followerId.equals(followingId)) {
            throw new IllegalArgumentException("Can't unfollow yourself");
        }
       Friend friend = friendRepository.findByFollowerIdAndFollowingId(followerId,followingId).orElseThrow(
               () -> new IllegalArgumentException("Not following the user")
       );
        friendRepository.delete(friend);
    }

    @Override
    public List<FriendResponseDTO> getAllFollowers(Long userId) {

      return friendRepository.findByFollowingId(userId).stream()
              .map(mapper::toResponse)
              .collect(Collectors.toList());
    }

    @Override
    public List<FriendResponseDTO> getAllFollowing(Long userId) {
       return friendRepository.findByFollowerId(userId).stream()
               .map(friend -> mapper.toResponse(friend))
               .collect(Collectors.toList());

    }
}
