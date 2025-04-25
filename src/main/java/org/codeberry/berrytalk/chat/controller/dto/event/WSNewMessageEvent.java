package org.codeberry.berrytalk.chat.controller.dto.event;

import org.codeberry.berrytalk.chat.controller.dto.MessageData;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WSNewMessageEvent extends WSChatEvent {
  private final MessageData message;

  @JsonCreator
  public WSNewMessageEvent(
      @JsonProperty("chatId") String chatId,
      @JsonProperty("message") MessageData message) {
    super(WSChatEventType.NEW_MESSAGE, chatId);
    this.message = message;
  }
}
