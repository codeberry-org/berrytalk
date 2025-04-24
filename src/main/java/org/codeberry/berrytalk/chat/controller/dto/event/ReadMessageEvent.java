package org.codeberry.berrytalk.chat.controller.dto.event;

import org.codeberry.berrytalk.chat.domain.event.ChatEvent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ReadMessageEvent extends ChatEvent {
  public static final String TYPE = "READ_MESSAGE";

  private final String lastMessageId;

  @JsonCreator
  public ReadMessageEvent(
      @JsonProperty("chatId") String chatId,
      @JsonProperty("lastMessageId") String lastMessageId) {
    super(chatId, TYPE);
    this.lastMessageId = lastMessageId;
  }
}
