package org.codeberry.berrytalk.chat.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codeberry.berrytalk.chat.app.ChatService;
import org.codeberry.berrytalk.chat.app.dto.ChatDetailInfo;
import org.codeberry.berrytalk.chat.app.dto.ChatInfo;
import org.codeberry.berrytalk.chat.app.dto.MessageRequest;
import org.codeberry.berrytalk.chat.controller.dto.Client;
import org.codeberry.berrytalk.chat.controller.dto.CreateChatRequest;
import org.codeberry.berrytalk.chat.controller.dto.InviteChatRequest;
import org.codeberry.berrytalk.chat.controller.dto.UpdateChatRequest;
import org.codeberry.berrytalk.chat.controller.dto.event.AddMessageEvent;
import org.codeberry.berrytalk.chat.controller.dto.event.ReadMessageEvent;
import org.codeberry.berrytalk.chat.domain.event.ChatEvent;
import org.codeberry.berrytalk.common.model.RestResponse;
import org.codeberry.berrytalk.common.util.JsonUtil;
import org.codeberry.berrytalk.common.ws.WebSocketMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chat")
@WebSocketMapping("/ws/v1/chat")
@RequiredArgsConstructor
public class ChatController extends TextWebSocketHandler {
  private static final Map<String, Class<? extends ChatEvent>> CHAT_EVENT_MAP = new HashMap<>() {{
    put(AddMessageEvent.TYPE, AddMessageEvent.class);
    put(ReadMessageEvent.TYPE, ReadMessageEvent.class);
  }};

  private final ChatService chatService;

  @PostMapping(value = {"/", ""})
  public RestResponse<ChatInfo> createChat(Client client,
      @RequestBody CreateChatRequest request) {
    ChatInfo chatInfo = chatService.createChat(client.userId(), request.inviteeIds());
    return new RestResponse<>(chatInfo);
  }

  @PutMapping("/{chatId}")
  public RestResponse<ChatInfo> updateChat(Client client,
      @PathVariable("chatId") String chatId,
      @RequestBody UpdateChatRequest request) {
    ChatInfo chatInfo = null;
    if (request.title() != null) {
      chatInfo = chatService.updateChatTitle(client.userId(), chatId, request.title());
    }
    if (request.imageId() != null) {
      chatInfo = chatService.updateChatImage(client.userId(), chatId, request.imageId());
    }
    return new RestResponse<ChatInfo>(chatInfo);
  }

  @PostMapping("/{chatId}/invite")
  public RestResponse<ChatInfo> inviteChat(Client client,
      @PathVariable("chatId") String chatId,
      @RequestBody InviteChatRequest request) {
    ChatInfo chatInfo = chatService.inviteChat(client.userId(), chatId, request.inviteeIds());
    return new RestResponse<ChatInfo>(chatInfo);
  }

  @PostMapping("/{chatId}/leave")
  public RestResponse<ChatInfo> leaveChat(Client client,
      @PathVariable("chatId") String chatId) {
    ChatInfo chatInfo = chatService.leaveChat(client.userId(), chatId);
    return new RestResponse<ChatInfo>(chatInfo);
  }

  @GetMapping(value = { "/", "" })
  public RestResponse<List<ChatDetailInfo>> retrieveChat(Client client,
      @RequestParam(name = "updatedAfter") Date updatedAfter) {
    List<ChatDetailInfo> chatInfos = chatService.retrieveChat(client.userId(), updatedAfter);
    return new RestResponse<>(chatInfos);
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    chatService.registerMessageSession(new WebSocketChatSession(session));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    chatService.deregisterMessageSession(session.getId());
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    Client client = Client.from(session.getHandshakeHeaders());
    ChatEvent chatEvent = parseChatEvent(message.getPayload());

    if (chatEvent instanceof AddMessageEvent addMessageEvent) {
      handleAddMessageEvent(client, addMessageEvent);
    } else if (chatEvent instanceof ReadMessageEvent readMessageEvent) {
      handleReadMessageEvent(client, readMessageEvent);
    }
  }

  private ChatEvent parseChatEvent(String payload) {
    String type = JsonUtil.findStringField(payload, "type")
        .orElseThrow(() -> new RuntimeException("Invalid ChatEvent - type not found"));
    Class<? extends ChatEvent> eventClazz = CHAT_EVENT_MAP.get(type);
    if (eventClazz == null) {
      throw new RuntimeException("Invalid ChatEvent - unknown type: " + type);
    }
    return JsonUtil.fromJson(payload, eventClazz);
  }

  private void handleAddMessageEvent(Client client, AddMessageEvent addMessageEvent) {
    MessageRequest messageRequest = switch (addMessageEvent.getMessageType()) {
      case TEXT -> MessageRequest.newTextMessageRequest(addMessageEvent.getText());
      case MEDIA -> MessageRequest.newMediaMessageRequest(addMessageEvent.getMedia());
    };
    chatService.addMessage(client.userId(), addMessageEvent.getChatId(), messageRequest);
  }
  
  private void handleReadMessageEvent(Client client, ReadMessageEvent readMessageEvent) {
    chatService.readMessage(client.userId(), readMessageEvent.getChatId(), readMessageEvent.getLastMessageId());
  }

}
