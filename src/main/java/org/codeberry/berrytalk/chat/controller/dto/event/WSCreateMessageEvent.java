package org.codeberry.berrytalk.chat.controller.dto.event;

import java.util.List;

import org.codeberry.berrytalk.common.model.Media;
import org.codeberry.berrytalk.common.model.MessageType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WSCreateMessageEvent extends WSChatEvent {
  public static record Message(
      MessageType type,
      String text,
      List<Media> media) {
  }

  private final Message message;

  @JsonCreator
  public WSCreateMessageEvent(
      @JsonProperty("chatId") String chatId,
      @JsonProperty("message") Message message) {
    super(WSChatEventType.CREATE_MESSAGE, chatId);
    this.message = message;
  }
}
