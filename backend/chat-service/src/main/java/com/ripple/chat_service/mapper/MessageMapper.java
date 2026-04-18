package com.ripple.chat_service.mapper;

import com.ripple.chat_service.document.Message;
import com.ripple.chat_service.dto.MessageResponseDTO;
import com.ripple.chat_service.dto.SendMessageRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public Message toDocument(SendMessageRequestDTO request, Long sendedId){
        Message message = new Message();
        message.setContent(request.getContent());
        message.setId(request.getConversationId());
        message.setReceiverId(request.getReceiverId());
        message.setSenderId(sendedId);
        return message;
    }

   public Message toDocument(SendMessageRequestDTO request, Long senderId) {
    Message message = new Message();
    message.setConversationId(request.getConversationId());
    message.setReceiverId(request.getReceiverId());
    message.setSenderId(senderId);
    message.setContent(request.getContent());
    message.setTimestamp(LocalDateTime.now());
    message.setStatus("SENT");
    message.setDelivered(false);
    message.setSeen(false);
    return message;
}
}
