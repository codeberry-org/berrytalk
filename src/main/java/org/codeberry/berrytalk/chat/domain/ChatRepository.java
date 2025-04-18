package org.codeberry.berrytalk.chat.domain;

import java.util.List;
import java.util.Optional;

public interface ChatRepository {
  
  void save(Chat chat);
  Optional<Chat> findById(String id);
  List<ChatDetail> findDetailsByUser(String userId, int size);
  List<ChatDetail> findDetailsByUser(String userId, String prevChatId, int size);
}
