package com.ripple.friend_service.controller;

import com.ripple.friend_service.dto.FriendRequestDTO;
import com.ripple.friend_service.dto.FriendResponseDTO;
import com.ripple.friend_service.dto.FriendshipCountResponseDTO;
import com.ripple.friend_service.response.ApiResponse;
import com.ripple.friend_service.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService service;

    @PostMapping("/follow")
    public ResponseEntity<ApiResponse<FriendResponseDTO>> follow(@RequestBody FriendRequestDTO request, @RequestHeader("X-User-Id") Long followerId){
        FriendResponseDTO response = service.follow(followerId,request.getFollowingId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Started following", response));
    }

    @DeleteMapping("/unfollow/{followingId}")
    public ResponseEntity<ApiResponse<Void>> unfollow(@PathVariable Long followingId, @RequestHeader("X-User-Id") Long followerId){
        service.unfollow(followerId,followingId);
        return ResponseEntity.ok(ApiResponse.success("Unfollowed Successfully", null));
    }

    @GetMapping ("/followers/{userId}")
    public ResponseEntity<ApiResponse<List<FriendResponseDTO>>> getAllFollowers(@PathVariable Long userId){
        List<FriendResponseDTO> followers = service.getAllFollowers(userId);
        return ResponseEntity.ok().body(ApiResponse.success("All followers list", followers));
    }

    @GetMapping ("/following/{userId}")
    public ResponseEntity<ApiResponse<List<FriendResponseDTO>>> getAllFollowing(@PathVariable Long userId){
        List<FriendResponseDTO> following = service.getAllFollowing(userId);
        return ResponseEntity.ok().body(ApiResponse.success("All following list", following));
    }

    @GetMapping("/counts/{userId}")
    public ResponseEntity<ApiResponse<FriendshipCountResponseDTO>> getCountOfFollowers(@PathVariable Long userId, @RequestHeader("X-User-Id")Long currentUserId){
        FriendshipCountResponseDTO response = new FriendshipCountResponseDTO();
        response.setFollowerCount(service.getCountOfFollowers(userId));
        response.setFollowingCount(service.getCountOfFollowing(userId));
        response.setIsFollowing(service.isFollowing(userId,currentUserId));
        return ResponseEntity.ok(ApiResponse.success("Counts fetched successfully",response));
    }


}
