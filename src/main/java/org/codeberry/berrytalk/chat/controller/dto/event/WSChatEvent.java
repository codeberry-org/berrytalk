package org.codeberry.berrytalk.chat.controller.dto.event;

import org.codeberry.berrytalk.chat.controller.dto.ChatUserData;
import org.codeberry.berrytalk.chat.controller.dto.MessageData;
import org.codeberry.berrytalk.chat.domain.event.ChatEvent;
import org.codeberry.berrytalk.chat.domain.event.NewMessageEvent;
import org.codeberry.berrytalk.chat.domain.event.SeenMessageEvent;
import org.codeberry.berrytalk.chat.domain.event.UpdateChatEvent;
import org.codeberry.berrytalk.common.util.JsonUtil;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class WSChatEvent {
  private final WSChatEventType type;
  private final String chatId;

  WSChatEvent(WSChatEventType type, String chatId) {
    this.type = type;
    this.chatId = chatId;
  }
  
  public static WSChatEvent parse(String payload) {
    String type = JsonUtil.findStringField(payload, "type")
        .orElseThrow(() -> new RuntimeException("Invalid ChatEvent - 'type' not found"));
    return switch (WSChatEventType.valueOf(type)) {
      case CREATE_MESSAGE -> JsonUtil.fromJson(payload, WSCreateMessageEvent.class);
      case READ_MESSAGE -> JsonUtil.fromJson(payload, WSReadMessageEvent.class);
      case NEW_MESSAGE -> JsonUtil.fromJson(payload, WSNewMessageEvent.class);
      case SEEN_MESSAGE -> JsonUtil.fromJson(payload, WSSeenMessageEvent.class);
      case UPDATE_CHAT -> JsonUtil.fromJson(payload, WSUpdateChatEvent.class);
    };
  }

  public static WSChatEvent from(ChatEvent chatEvent) {
    return switch (chatEvent.getType()) {
      case NEW_MESSAGE -> {
        NewMessageEvent newMessageEvent = (NewMessageEvent) chatEvent;
        yield new WSNewMessageEvent(newMessageEvent.getChatId(),
            MessageData.from(newMessageEvent.getMessage()));
      }
      case SEEN_MESSAGE -> {
        SeenMessageEvent seenMessageEvent = (SeenMessageEvent) chatEvent;
        yield new WSSeenMessageEvent(seenMessageEvent.getChatId(),
            seenMessageEvent.getUserId(),
            seenMessageEvent.getLastMessageId());
      }
      case UPDATE_CHAT -> {
        UpdateChatEvent updateChatEvent = (UpdateChatEvent) chatEvent;
        yield new WSUpdateChatEvent(updateChatEvent.getChatId(),
            new WSUpdateChatEvent.Chat(
                updateChatEvent.getTitle(),
                updateChatEvent.getImageId(),
                updateChatEvent.getUsers().stream().map(ChatUserData::from).toList()));
      }
    };
  }
}
