package org.codeberry.berrytalk.chat.domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.codeberry.berrytalk.chat.common.model.Media;

import lombok.Getter;

@Getter
public class ChatUser {
  private final String chatId;
  private final String userId;
  private final Boolean isCreator;
  private final Date joinedAt;
  private String lastMessageId;

  ChatUser(String chatId, String userId, boolean isCreator) {
    this.chatId = chatId;
    this.userId = userId;
    this.isCreator = isCreator;
    this.joinedAt = new Date();
  }
  
  public ChatUser(String chatId, String userId, Boolean isCreator, Date joinedAt, String lastMessageId) {
    this.chatId = chatId;
    this.userId = userId;
    this.isCreator = isCreator;
    this.joinedAt = joinedAt;
    this.lastMessageId = lastMessageId;
  }

  void updateLastMessageId(String lastMessageId) {
    this.lastMessageId = lastMessageId;
  }

  public TextMessage createTextMessage(String text) {
    return new TextMessage(chatId, userId, text);
  }

  public MediaMessage createMediaMessage(List<Media> media) {
    return new MediaMessage(chatId, userId, media);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chatId, userId);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof ChatUser && ((ChatUser) obj).chatId.equals(chatId) && ((ChatUser) obj).userId.equals(userId);
  }

}
