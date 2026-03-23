package com.ripple.user_service.dto;

public class TokenResponseDTO {

    private String token;
    private long expiresInMs;
    private String tokenType;

    public TokenResponseDTO(String token, long expiresInMs) {
        this.token = token;
        this.expiresInMs = expiresInMs;
        this.tokenType = "Bearer";
    }

    public String getToken() { return token; }
    public long getExpiresInMs() { return expiresInMs; }
    public String getTokenType() { return tokenType; }
}