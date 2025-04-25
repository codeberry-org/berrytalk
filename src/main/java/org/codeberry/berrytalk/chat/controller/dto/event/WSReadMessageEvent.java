package org.codeberry.berrytalk.chat.controller.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WSReadMessageEvent extends WSChatEvent {
  private final String lastMessageId;
  
  @JsonCreator
  public WSReadMessageEvent(
      @JsonProperty("chatId") String chatId,
      @JsonProperty("lastMessageId") String lastMessageId) {
    super(WSChatEventType.READ_MESSAGE, chatId);
    this.lastMessageId = lastMessageId;
  }
}
