package org.codeberry.berrytalk.chat.domain;

import java.util.Date;

public class TextMessage extends Message {
  private final String text;

  TextMessage(String chatId, String userId, String text) {
    super(chatId, userId);
    this.text = text;
  }

  public TextMessage(String id, String chatId, String userId, String text, Date createdAt) {
    super(id, chatId, userId, createdAt);
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public String getTitle() {
    return text;
  }
  
}
