package com.ripple.user_service.mapper;


import com.ripple.user_service.dto.UserRequest;
import com.ripple.user_service.dto.UserResponse;
import com.ripple.user_service.entity.User;

public class UserMapper {
    private UserMapper(){};
    public static User toEntity (UserRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        return user;
    }

    public static UserResponse response(User user){
        return new UserResponse(user.getId(),user.getEmail(), user.getUsername(), user.getCreatedAt() );
    }
}
