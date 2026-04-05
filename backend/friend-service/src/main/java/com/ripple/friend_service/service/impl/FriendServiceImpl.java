package com.ripple.friend_service.service.impl;

import com.ripple.friend_service.client.UserDTO;
import com.ripple.friend_service.client.UserServiceClient;
import com.ripple.friend_service.dto.EnrichedFriendResponseDTO;
import com.ripple.friend_service.dto.FriendResponseDTO;
import com.ripple.friend_service.entity.Friend;
import com.ripple.friend_service.events.FriendEvent;
import com.ripple.friend_service.events.FriendEventType;
import com.ripple.friend_service.exception.AlreadyFollowingException;
import com.ripple.friend_service.exception.FriendshipNotFoundException;
import com.ripple.friend_service.exception.SelfFollowException;
import com.ripple.friend_service.mapper.FriendMapper;
import com.ripple.friend_service.repository.FriendRepository;
import com.ripple.friend_service.service.CacheService;
import com.ripple.friend_service.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    public final FriendRepository friendRepository;
    public final FriendMapper mapper;
    public final CacheService cacheService;
    public final KafkaTemplate<String, FriendEvent> kafkaTemplate;
    public final UserServiceClient userServiceClient;

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

       FriendEvent friendEvent = new FriendEvent( followerId, followingId,LocalDateTime.now() ,FriendEventType.FRIEND_ADDED );
        kafkaTemplate.send("friend.added",friendEvent);
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
        FriendEvent friendEvent = new FriendEvent( followerId, followingId,LocalDateTime.now() ,FriendEventType.FRIEND_REMOVED );
        kafkaTemplate.send("friend.removed",friendEvent);
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

    @Override
    public Boolean isFollowing(Long userId, Long currentUserId){
        return !userId.equals(currentUserId) &&  friendRepository.existsByFollowerIdAndFollowingId(currentUserId,userId);
    }

    @Override
    public List<EnrichedFriendResponseDTO> getEnrichedFollowers(Long userId) {
        List<Friend> followers = friendRepository.findByFollowingId(userId);
        List<Long> followerIds = followers.stream()
                .map(Friend::getFollowerId)
                .collect(Collectors.toList());

        List<UserDTO> users = userServiceClient.getUsersByIds(followerIds);
        Map<Long, UserDTO> userMap = users.stream()
                .collect(Collectors.toMap(UserDTO::getId, u -> u));

        return followers.stream().map(friend -> {
            EnrichedFriendResponseDTO dto = new EnrichedFriendResponseDTO();
            UserDTO user = userMap.get(friend.getFollowerId());
            dto.setUserId(friend.getFollowerId());
            dto.setUsername(user != null ? user.getUsername() : "Unknown");
            dto.setEmail(user != null ? user.getEmail() : "");
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<EnrichedFriendResponseDTO> getEnrichedFollowing(Long userId) {
        List<Friend> following = friendRepository.findByFollowerId(userId);
        List<Long> followingIds = following.stream()
                .map(Friend::getFollowingId)
                .collect(Collectors.toList());

        List<UserDTO> users = userServiceClient.getUsersByIds(followingIds);
        Map<Long, UserDTO> userMap = users.stream()
                .collect(Collectors.toMap(UserDTO::getId, u -> u));

        return following.stream().map(friend -> {
            EnrichedFriendResponseDTO dto = new EnrichedFriendResponseDTO();
            UserDTO user = userMap.get(friend.getFollowingId());
            dto.setUserId(friend.getFollowingId());
            dto.setUsername(user != null ? user.getUsername() : "Unknown");
            dto.setEmail(user != null ? user.getEmail() : "");
            return dto;
        }).collect(Collectors.toList());
    }
}
