package org.codeberry.berrytalk.chat.app.dto;

import java.util.List;

import org.codeberry.berrytalk.common.model.Media;
import org.codeberry.berrytalk.common.model.MessageType;
import org.codeberry.berrytalk.common.util.Args;

public record MessageRequest(
    MessageType type,
    String text,
    List<Media> media) {

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
