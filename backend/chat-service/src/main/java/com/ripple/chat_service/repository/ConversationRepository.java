package com.ripple.chat_service.repository;

import com.ripple.chat_service.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByParticipant1IdOrParticipant2IdOrderByLastMessageTimeDesc(Long participant1Id, Long participant2Id);
    Optional<Conversation> findByParticipant1IdAndParticipant2Id(Long participant1Id, Long participant2Id);

}
