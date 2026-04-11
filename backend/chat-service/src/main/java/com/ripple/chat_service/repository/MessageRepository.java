package com.ripple.chat_service.repository;

import com.ripple.chat_service.document.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    // Get messages for a conversation ordered by time — for chat screen
    List<Message> findByConversationIdOrderByTimestampAsc(String conversationId);

    // Get undelivered messages for a user — for offline delivery
    List<Message> findByReceiverIdAndIsDeliveredFalse(Long receiverId);
}
