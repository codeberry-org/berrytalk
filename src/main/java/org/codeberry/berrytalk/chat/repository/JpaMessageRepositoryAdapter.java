package org.codeberry.berrytalk.chat.repository;

import java.util.List;

import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.chat.domain.MessageRepository;

public class JpaMessageRepositoryAdapter implements MessageRepository {

  @Override
  public void save(Message message) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public List<Message> findAll(String chatId, int size) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public List<Message> findAll(String chatId, String prevMessageId, int size) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }
  
}
