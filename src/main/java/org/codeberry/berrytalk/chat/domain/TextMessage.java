package org.codeberry.berrytalk.chat.domain;

import java.util.Date;

import org.codeberry.berrytalk.chat.common.model.MessageType;
import org.codeberry.berrytalk.chat.common.util.Args;

public class TextMessage extends Message {
  private final String text;

  TextMessage(String chatId, String userId, String text) {
    super(chatId, userId);
    this.text = Args.requireNotEmpty(text, "text");
  }

  public TextMessage(String id, String chatId, String userId, Date createdAt, String text) {
    super(id, chatId, userId, createdAt);
    this.text = Args.requireNotEmpty(text, "text");
  }

  @Override
  public MessageType getType() {
    return MessageType.TEXT;
  }

  @Override
  public String getTitle() {
    return text;
  }

  @Override
  public String getContent() {
    return text;
  }
  
}
