package org.codeberry.berrytalk.chat.controller.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WSSeenMessageEvent extends WSChatEvent {
  private final String userId;
  private final String lastMessageId;
  
  @JsonCreator
  public WSSeenMessageEvent(
      @JsonProperty("chatId") String chatId,
      @JsonProperty("userId") String userId,
      @JsonProperty("lastMessageId") String lastMessageId) {
    super(WSChatEventType.SEEN_MESSAGE, chatId);
    this.userId = userId;
    this.lastMessageId = lastMessageId;
  }
}
