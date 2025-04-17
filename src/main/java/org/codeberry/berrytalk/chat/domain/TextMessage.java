package org.codeberry.berrytalk.chat.domain;

import java.util.Date;

import lombok.Getter;

@Getter
public class TextMessage extends Message {
  private final String text;

  TextMessage(String chatId, String userId, String text) {
    super(chatId, userId);
    this.text = text;
  }

  TextMessage(String id, String chatId, String userId, String text, Date createdAt) {
    super(id, chatId, userId, createdAt);
    this.text = text;
  }

  @Override
  String getTitle() {
    return text;
  }
  
}
