package com.ripple.chat_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConversationResponseDTO {
    private Long id;
    private Long participant1Id;
    private Long participant2Id;
    private String participant2Username;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private Long unreadCount;
}
