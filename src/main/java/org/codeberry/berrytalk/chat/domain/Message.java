package org.codeberry.berrytalk.chat.domain;

import java.util.Date;

import org.codeberry.berrytalk.chat.common.util.IdUtil;

public abstract class Message {
  public static final String ID_PREFIX = "msg";

  private final String id;
  private final String chatId;
  private final String userId;
  private final Date createdAt;

  Message(String chatId, String userId) {
    this.id = IdUtil.create(ID_PREFIX);
    this.chatId = chatId;
    this.userId = userId;
    this.createdAt = new Date();
  }

  public Message(String id, String chatId, String userId, Date createdAt) {
    this.id = id;
    this.chatId = chatId;
    this.userId = userId;
    this.createdAt = createdAt;
  }

  public String getId() {
    return id;
  }

  public String getChatId() {
    return chatId;
  }

  public String getUserId() {
    return userId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public abstract String getTitle();
}
