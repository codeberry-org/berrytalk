package org.codeberry.berrytalk.chat.controller.dto;

import java.util.Date;
import java.util.List;

import org.codeberry.berrytalk.chat.app.dto.MessageInfo;
import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.common.model.Media;
import org.codeberry.berrytalk.common.model.MessageType;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MessageData(
    String id,
    String chatId,
    String userId,
    MessageType type,
    String text,
    List<Media> media,
    Date createdAt) {

  public static MessageData from(MessageInfo messageInfo) {
    if (messageInfo == null) {
      return null;
    }
    return new MessageData(
      messageInfo.id(),
      messageInfo.chatId(),
      messageInfo.userId(),
      messageInfo.type(),
      messageInfo.text(),
      messageInfo.media(),
      messageInfo.createdAt());
  }

  public static MessageData from(Message message) {
    return from(MessageInfo.from(message));
  }
}
