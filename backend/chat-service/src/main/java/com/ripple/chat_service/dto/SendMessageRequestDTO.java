package com.ripple.chat_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendMessageRequestDTO {

    @NotNull
    private String conversationId;

    @NotBlank
    private String content;

    @NotNull
    private Long receiverId;

}
