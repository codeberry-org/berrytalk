package org.codeberry.berrytalk.chat.domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.codeberry.berrytalk.common.model.Media;
import org.codeberry.berrytalk.common.util.Args;

public class ChatUser {
  private final String chatId;
  private final String userId;
  private final boolean isCreator;
  private final Date joinedAt;
  private String lastMessageId;

  ChatUser(String chatId, String userId, boolean isCreator) {
    this.chatId = Args.requireNotEmpty(chatId, "chatId");
    this.userId = Args.requireNotEmpty(userId, "userId");
    this.isCreator = isCreator;
    this.joinedAt = new Date();
  }
  
  public ChatUser(String chatId, String userId, boolean isCreator, Date joinedAt, String lastMessageId) {
    this.chatId = Args.requireNotEmpty(chatId, "chatId");
    this.userId = Args.requireNotEmpty(userId, "userId");
    this.isCreator = isCreator;
    this.joinedAt = Args.requireNotNull(joinedAt, "joinedAt");
    this.lastMessageId = lastMessageId;
  }

  public String getChatId() {
    return chatId;
  }

  public String getUserId() {
    return userId;
  }

  public boolean getIsCreator() {
    return isCreator;
  }

  public Date getJoinedAt() {
    return joinedAt;
  }

  void updateLastMessageId(String lastMessageId) {
    this.lastMessageId = lastMessageId;
  }

  public String getLastMessageId() {
    return lastMessageId;
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
