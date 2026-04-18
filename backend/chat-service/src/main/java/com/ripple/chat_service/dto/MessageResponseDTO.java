package com.ripple.chat_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponseDTO {
    private String id;
    private String content;
    private String conversationId;
    private Long senderId;
    private Long receiverId;
    private LocalDateTime timestamp;
    private boolean isDelivered;
    private boolean isSeen;
    private String status;
}
