package org.codeberry.berrytalk.chat.app.dto;

import java.util.Date;
import java.util.List;

import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.common.model.Media;
import org.codeberry.berrytalk.common.model.MessageType;

public record MessageInfo(
    String id,
    String chatId,
    String userId,
    MessageType type,
    String text,
    List<Media> media,
    Date createdAt) {

  @SuppressWarnings("unchecked")
  public static MessageInfo from(Message message) {
    if (message == null) {
      return null;
    }
    return switch (message.getType()) {
      case TEXT -> new MessageInfo(
          message.getId(),
          message.getChatId(),
          message.getUserId(),
          message.getType(),
          (String)message.getContent(),
          null,
          message.getCreatedAt());
      case MEDIA -> new MessageInfo(
          message.getId(),
          message.getChatId(),
          message.getUserId(),
          message.getType(),
          null,
          (List<Media>) message.getContent(),
          message.getCreatedAt());
    };
  }
}
