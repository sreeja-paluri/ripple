package com.ripple.friend_service.exception;

public class SelfFollowException extends RuntimeException{
    public SelfFollowException(String message){
        super(message);
    }
}