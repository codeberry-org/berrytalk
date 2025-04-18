package org.codeberry.berrytalk.chat.app.dto;

import java.util.List;
import java.util.Objects;

import org.codeberry.berrytalk.chat.common.model.Media;

public record MessageRequest(
    MessageType type,
    String text,
    List<Media> media) {

  public MessageRequest {
    throw new UnsupportedOperationException("Use MessageRequest.of() instead.");
  }

  public static MessageRequest newTextMessage(String text) {
    Objects.requireNonNull(text);
    if (text.isEmpty()) {
      throw new IllegalArgumentException("Text should not be empty");
    }
    return new MessageRequest(MessageType.TEXT, text, null);
  }

  public static MessageRequest newMediaMessage(List<Media> media) {
    Objects.requireNonNull(media);
    if (media.isEmpty()) {
      throw new IllegalArgumentException("More than one media is required");
    }
    return new MessageRequest(MessageType.MEDIA, null, media);
  }

}
