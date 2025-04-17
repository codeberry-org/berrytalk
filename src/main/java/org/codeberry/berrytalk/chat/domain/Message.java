package org.codeberry.berrytalk.chat.domain;

import java.util.Date;

import org.codeberry.berrytalk.chat.common.util.IdUtil;

import lombok.Getter;

@Getter
public abstract class Message {
  private static final String ID_PREFIX = "msg";

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

  Message(String id, String chatId, String userId, Date createdAt) {
    this.id = id;
    this.chatId = chatId;
    this.userId = userId;
    this.createdAt = createdAt;
  }

  abstract String getTitle();
}
