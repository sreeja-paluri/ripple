package com.ripple.friend_service.service.impl;

import com.ripple.friend_service.dto.FriendResponseDTO;
import com.ripple.friend_service.entity.Friend;
import com.ripple.friend_service.exception.AlreadyFollowingException;
import com.ripple.friend_service.exception.FriendshipNotFoundException;
import com.ripple.friend_service.exception.SelfFollowException;
import com.ripple.friend_service.mapper.FriendMapper;
import com.ripple.friend_service.repository.FriendRepository;
import com.ripple.friend_service.service.CacheService;
import com.ripple.friend_service.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    public final FriendRepository friendRepository;
    public final FriendMapper mapper;
    public final CacheService cacheService;
    @Override
    public FriendResponseDTO follow(Long followerId, Long followingId){
        if(followerId.equals(followingId)){
            throw new SelfFollowException("Can't follow yourself");
        }
        if(friendRepository.existsByFollowerIdAndFollowingId(followerId,followingId)){
            throw new AlreadyFollowingException("Already following this user");
        }
        Friend friend = new Friend();
        friend.setFollowingId(followingId);
        friend.setFollowerId(followerId);
        Friend saved = friendRepository.save(friend);
        cacheService.incrementFollowerCount(followingId);
        cacheService.incrementFollowingCount(followerId);
        return mapper.toResponse(saved);
    }

    @Override
    public void unfollow(Long followerId, Long followingId) {
        if(followerId.equals(followingId)) {
            throw new SelfFollowException("Can't unfollow yourself");
        }
       Friend friend = friendRepository.findByFollowerIdAndFollowingId(followerId,followingId).orElseThrow(
               () -> new FriendshipNotFoundException("Not following the user")
       );
        friendRepository.delete(friend);
        cacheService.decrementFollowerCount(followingId);
        cacheService.decrementFollowingCount(followerId);
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

    @Override
    public Long getCountOfFollowers(Long userId){
        Long count = cacheService.getFollowerCount(userId);
        if (count == null) {
            count = friendRepository.countByFollowingId(userId);
            cacheService.setFollowerCount(userId,count);
        }
        return count;
    }

    @Override
    public Long getCountOfFollowing(Long userId){
      Long count = cacheService.getFollowingCount(userId);
      if(count == null){
          count = friendRepository.countByFollowerId(userId);
          cacheService.setFollowingCount(userId,count);
      }
      return count;
    }
}
