package com.ripple.chat_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateConversationRequestDTO {
    @NotNull
    private Long participant2Id;
}
