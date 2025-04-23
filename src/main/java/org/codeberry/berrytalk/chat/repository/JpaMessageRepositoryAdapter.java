package org.codeberry.berrytalk.chat.repository;

import java.util.List;

import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.chat.domain.MessageRepository;
import org.codeberry.berrytalk.chat.repository.entity.MessageEntity;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaMessageRepositoryAdapter implements MessageRepository {

  private final JpaMessageRepository jpaMessageRepository;
  private final QJpaMessageRepository qJpaMessageRepository;

  @Override
  public void save(Message message) {
    jpaMessageRepository.save(MessageEntity.from(message));
  }

  @Override
  public List<Message> findAll(String chatId, int size) {
    return qJpaMessageRepository.findAll(chatId, size).stream()
        .map(MessageEntity::toMessage)
        .toList();
  }

  @Override
  public List<Message> findAll(String chatId, String prevMessageId, int size) {
    return qJpaMessageRepository.findAll(chatId, prevMessageId, size).stream()
        .map(MessageEntity::toMessage)
        .toList();
  }
  
}
