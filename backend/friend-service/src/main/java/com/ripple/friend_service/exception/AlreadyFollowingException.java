package com.ripple.friend_service.exception;

public class AlreadyFollowingException extends RuntimeException{
    public AlreadyFollowingException(String message){
        super(message);
    }
}