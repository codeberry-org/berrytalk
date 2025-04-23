package org.codeberry.berrytalk.chat.app.dto;

import java.util.Date;

import org.codeberry.berrytalk.chat.common.model.MessageType;
import org.codeberry.berrytalk.chat.domain.Message;

public record MessageInfo(
    String id,
    String chatId,
    String userId,
    MessageType type,
    Object content,
    Date createdAt) {

  public static MessageInfo from(Message message) {
    if (message == null) {
      return null;
    }
    return new MessageInfo(
        message.getId(),
        message.getChatId(),
        message.getUserId(),
        message.getType(),
        message.getContent(),
        message.getCreatedAt());
  }
}
