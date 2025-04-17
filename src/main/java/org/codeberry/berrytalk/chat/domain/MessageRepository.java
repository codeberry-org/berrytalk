package org.codeberry.berrytalk.chat.domain;

import java.util.List;

public interface MessageRepository {

  List<Message> findAll(String chatId, int size);
  List<Message> findAll(String chatId, String prevMessageId, int size);
}
