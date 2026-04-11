package com.ripple.chat_service.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
@CompoundIndex(name = "idx_conversation_timestamp", def = "{'conversationId': 1, 'timestamp': 1}")
public class Message {

    @Id
    private String id;

    private String conversationId;

    private Long senderId;

    private Long receiverId;

    private String content;

    private LocalDateTime timestamp;

    private boolean isDelivered = false;

    private boolean isSeen = false;

    private String status = "SENT";
}
