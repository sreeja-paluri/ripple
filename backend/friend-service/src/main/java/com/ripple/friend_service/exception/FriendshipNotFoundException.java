package com.ripple.friend_service.exception;


public class FriendshipNotFoundException extends RuntimeException{
    public FriendshipNotFoundException(String message){
        super(message);
    }
}
