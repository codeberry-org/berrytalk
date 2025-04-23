package org.codeberry.berrytalk.chat.controller.dto;

import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.common.model.MessageType;

public record NewMessage(
    String id,
    String chatId,
    String userId,
    MessageType type,
    Object content,
    Long timestamp) {
  
  public static NewMessage from(Message message) {
    return new NewMessage(
        message.getId(),
        message.getChatId(),
        message.getUserId(),
        message.getType(),
        message.getContent(),
        message.getCreatedAt().getTime());
  }
}
