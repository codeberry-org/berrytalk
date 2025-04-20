package org.codeberry.berrytalk.chat.domain;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChatRepository {
  
  void save(Chat chat);
  Optional<Chat> findById(String id);
  List<ChatDetail> findAllDetailByUser(String userId, Date updatedAfter);
}
