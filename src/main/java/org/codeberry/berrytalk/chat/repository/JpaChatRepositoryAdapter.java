package org.codeberry.berrytalk.chat.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.codeberry.berrytalk.chat.domain.Chat;
import org.codeberry.berrytalk.chat.domain.ChatDetail;
import org.codeberry.berrytalk.chat.domain.ChatRepository;
import org.codeberry.berrytalk.chat.repository.entity.ChatEntity;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaChatRepositoryAdapter implements ChatRepository {
  private final JpaChatRepository jpaChatRepository;
  private final QJpaChatRepository qJpaChatRepository;

  @Override
  public void save(Chat chat) {
    jpaChatRepository.save(ChatEntity.from(chat));
  }

  @Override
  public Optional<Chat> findById(String id) {
    return jpaChatRepository.findById(id)
        .map(ChatEntity::toChat);
  }

  @Override
  public List<ChatDetail> findAllDetailByUser(String userId, Date updatedAfter) {
    return qJpaChatRepository.findAllDetailByUser(userId, updatedAfter).stream()
        .map(ChatEntity::toChatDetail)
        .toList();
  }
  
}
