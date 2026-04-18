package com.ripple.chat_service.mapper;

import com.ripple.chat_service.dto.ConversationResponseDTO;
import com.ripple.chat_service.dto.CreateConversationRequestDTO;
import com.ripple.chat_service.entity.Conversation;
import org.springframework.stereotype.Component;

@Component
public class ConversationMapper {

    public Conversation toEntity(CreateConversationRequestDTO request, Long participant1Id) {
        Conversation conversation = new Conversation();
        conversation.setParticipant1Id(participant1Id);
        conversation.setParticipant2Id(request.getParticipant2Id());
        return conversation;
    }

    public ConversationResponseDTO toResponse(Conversation conversation) {
        ConversationResponseDTO response = new ConversationResponseDTO();
        response.setId(conversation.getId());
        response.setParticipant1Id(conversation.getParticipant1Id());
        response.setParticipant2Id(conversation.getParticipant2Id());
        response.setLastMessage(conversation.getLastMessage());
        response.setLastMessageTime(conversation.getLastMessageTime());
        return response;
    }
}
