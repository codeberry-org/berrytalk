package org.codeberry.berrytalk.chat.repository;

import java.util.List;
import java.util.Optional;

import org.codeberry.berrytalk.chat.domain.Chat;
import org.codeberry.berrytalk.chat.domain.ChatRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaChatRepositoryAdapter implements ChatRepository {

  @Override
  public void save(Chat chat) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public Optional<Chat> findById(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public List<Chat> findAllByUser(String userId, int size) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllByUser'");
  }

  @Override
  public List<Chat> findAllByUser(String userId, String prevChatId, int size) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllByUser'");
  }
  
}
