package com.ripple.friend_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheService {
    private final RedisTemplate<String, Long> redisTemplate;

    public void incrementFollowerCount(Long userId){
        try {
            redisTemplate.opsForValue().increment("followers:" + userId);
        } catch(Exception e){
            log.error("Redis is unavailable.Could not increase follower count for the user{}", userId);
        }
    }

   public void decrementFollowerCount(Long userId){
       try {
           redisTemplate.opsForValue().decrement("followers:" + userId);
       } catch(Exception e){
           log.error("Redis is unavailable.Could not decrease follower count for the user{}", userId);
       }
    }

    public void incrementFollowingCount(Long userId){
        try {
            redisTemplate.opsForValue().increment("following:" + userId);
        } catch(Exception e){
            log.error("Redis is unavailable.Could not increase following count for the user{}", userId);
        }
    }

   public void decrementFollowingCount(Long userId){
        try {
            redisTemplate.opsForValue().decrement("following:" + userId);
        } catch(Exception e){
            log.error("Redis is unavailable.Could not decrease following count for the user{}", userId);
        }
    }

    public Long getFollowerCount(Long userId)
    {
        try {
            return redisTemplate.opsForValue().get("followers:" + userId);
        } catch(Exception e){
            log.error("Redis is unavailable.Could not get follower count for the user{}", userId);
            return  null;
        }
    }

    public Long getFollowingCount(Long userId){
        try {
            return redisTemplate.opsForValue().get("following:" + userId);
        }catch(Exception e){
            log.error("Redis is unavailable.Could not get following count for the user{}", userId);
            return  null;
        }
    }

}
