package org.codeberry.berrytalk.chat.app.dto;

import java.util.List;

import org.codeberry.berrytalk.chat.common.model.Media;
import org.codeberry.berrytalk.chat.common.model.MessageType;
import org.codeberry.berrytalk.chat.common.util.Args;

public record MessageRequest(
    MessageType type,
    String text,
    List<Media> media) {

  public MessageRequest {
    throw new UnsupportedOperationException("Use MessageRequest.of() instead.");
  }

  public static MessageRequest newTextMessageRequest(String text) {
    return new MessageRequest(MessageType.TEXT,
        Args.requireNotEmpty(text, "text"),
        null);
  }

  public static MessageRequest newMediaMessageRequest(List<Media> media) {
    return new MessageRequest(MessageType.MEDIA,
        null,
        Args.requireNotEmpty(media, "media"));
  }

}
