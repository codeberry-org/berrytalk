package org.codeberry.berrytalk.chat.domain;

import java.util.Date;

import org.codeberry.berrytalk.chat.common.model.MessageType;
import org.codeberry.berrytalk.chat.common.util.Args;
import org.codeberry.berrytalk.chat.common.util.IdUtil;

public abstract class Message {
  public static final String ID_PREFIX = "msg";

  private final String id;
  private final String chatId;
  private final String userId;
  private final Date createdAt;

  Message(String chatId, String userId) {
    this.id = IdUtil.create(ID_PREFIX);
    this.chatId = Args.requireNotEmpty(chatId, "chatId");
    this.userId = Args.requireNotEmpty(userId, "userId");
    this.createdAt = new Date();
  }

  public Message(String id, String chatId, String userId, Date createdAt) {
    this.id = Args.requireNotEmpty(id, "id");
    this.chatId = Args.requireNotEmpty(chatId, "chatId");
    this.userId = Args.requireNotEmpty(userId, "userId");
    this.createdAt = Args.requireNotNull(createdAt, "createdAt");
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

  public abstract MessageType getType();
  public abstract String getTitle();
  public abstract Object getContent();
}
