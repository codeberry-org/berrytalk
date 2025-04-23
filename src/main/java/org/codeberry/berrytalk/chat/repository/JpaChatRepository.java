package org.codeberry.berrytalk.chat.repository;

import org.codeberry.berrytalk.chat.repository.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRepository extends JpaRepository<ChatEntity, String> {
  
}
