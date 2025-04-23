package org.codeberry.berrytalk.chat.repository;

import org.codeberry.berrytalk.chat.repository.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMessageRepository extends JpaRepository<MessageEntity, String> {
  
}
