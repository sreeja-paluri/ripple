package com.ripple.friend_service.repository;

import com.ripple.friend_service.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    List<Friend> findByFollowingId(Long followingId);

    List<Friend> findByFollowerId(Long followerId);

    Optional<Friend> findByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
