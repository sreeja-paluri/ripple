package com.ripple.user_service.dto;

public class TokenResponseDTO {

    private String token;
    private long expiresInMs;
    private String tokenType;

    private UserResponse user;

    public TokenResponseDTO(String token, long expiresInMs, UserResponse user) {
        this.token = token;
        this.expiresInMs = expiresInMs;
        this.tokenType = "Bearer";
        this.user = user;
    }

    public String getToken() { return token; }
    public long getExpiresInMs() { return expiresInMs; }
    public String getTokenType() { return tokenType; }
    public UserResponse getUser(){ return  user;}

}