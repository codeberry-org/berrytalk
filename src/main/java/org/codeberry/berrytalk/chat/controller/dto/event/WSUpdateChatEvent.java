package org.codeberry.berrytalk.chat.controller.dto.event;

import java.util.List;

import org.codeberry.berrytalk.chat.controller.dto.ChatUserData;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WSUpdateChatEvent extends WSChatEvent {
  public static record Chat(
      String title,
      String imageId,
      List<ChatUserData> users) {
  }

  private Chat chat;
  
  @JsonCreator
  public WSUpdateChatEvent(
      @JsonProperty("chatId") String chatId,
      @JsonProperty("chat") Chat chat) {
    super(WSChatEventType.UPDATE_CHAT, chatId);
    this.chat = chat;
  }
}
