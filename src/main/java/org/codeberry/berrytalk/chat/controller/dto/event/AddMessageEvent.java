package org.codeberry.berrytalk.chat.controller.dto.event;

import java.util.List;

import org.codeberry.berrytalk.chat.domain.event.ChatEvent;
import org.codeberry.berrytalk.common.model.Media;
import org.codeberry.berrytalk.common.model.MessageType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class AddMessageEvent extends ChatEvent {
  public static final String TYPE = "ADD_MESSAGE";

  private final MessageType messageType;
  private final String text;
  private final List<Media> media;

  @JsonCreator
  public AddMessageEvent(
      @JsonProperty("chatId") String chatId,
      @JsonProperty("messageType") MessageType messageType,
      @JsonProperty(value = "text", required = false) String text,
      @JsonProperty(value = "media", required = false) List<Media> media) {
    super(TYPE, chatId);
    this.messageType = messageType;
    this.text = text;
    this.media = media;
  }

}
