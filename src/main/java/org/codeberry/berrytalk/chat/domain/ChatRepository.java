package org.codeberry.berrytalk.chat.domain;

import java.util.List;
import java.util.Optional;

public interface ChatRepository {
  
  void save(Chat chat);
  Optional<Chat> findById(String id);
  List<Chat> findAllByUser(String userId, int size);
  List<Chat> findAllByUser(String userId, String prevChatId, int size);
}
