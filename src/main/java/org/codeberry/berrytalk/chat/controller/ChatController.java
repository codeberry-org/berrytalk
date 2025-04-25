package org.codeberry.berrytalk.chat.controller;

import java.util.Date;
import java.util.List;

import org.codeberry.berrytalk.chat.app.ChatService;
import org.codeberry.berrytalk.chat.app.dto.ChatDetailInfo;
import org.codeberry.berrytalk.chat.app.dto.ChatInfo;
import org.codeberry.berrytalk.chat.app.dto.MessageRequest;
import org.codeberry.berrytalk.chat.controller.dto.ChatData;
import org.codeberry.berrytalk.chat.controller.dto.Client;
import org.codeberry.berrytalk.chat.controller.dto.CreateChatRequest;
import org.codeberry.berrytalk.chat.controller.dto.InviteChatRequest;
import org.codeberry.berrytalk.chat.controller.dto.UpdateChatRequest;
import org.codeberry.berrytalk.chat.controller.dto.event.WSChatEvent;
import org.codeberry.berrytalk.chat.controller.dto.event.WSCreateMessageEvent;
import org.codeberry.berrytalk.chat.controller.dto.event.WSReadMessageEvent;
import org.codeberry.berrytalk.common.model.RestResponse;
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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/chat")
@WebSocketMapping("/ws/v1/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController extends TextWebSocketHandler {
  private final ChatService chatService;

  @PostMapping(value = {"/", ""})
  public RestResponse<ChatData> createChat(Client client,
      @RequestBody CreateChatRequest request) {
    ChatInfo chatInfo = chatService.createChat(client.userId(), request.inviteeIds());
    return new RestResponse<>(ChatData.from(chatInfo));
  }

  @PutMapping("/{chatId}")
  public RestResponse<ChatData> updateChat(Client client,
      @PathVariable("chatId") String chatId,
      @RequestBody UpdateChatRequest request) {
    ChatInfo chatInfo = null;
    if (request.title() != null) {
      chatInfo = chatService.updateChatTitle(client.userId(), chatId, request.title());
    }
    if (request.imageId() != null) {
      chatInfo = chatService.updateChatImage(client.userId(), chatId, request.imageId());
    }
    return new RestResponse<>(ChatData.from(chatInfo));
  }

  @PostMapping("/{chatId}/invite")
  public RestResponse<ChatData> inviteChat(Client client,
      @PathVariable("chatId") String chatId,
      @RequestBody InviteChatRequest request) {
    ChatInfo chatInfo = chatService.inviteChat(client.userId(), chatId, request.inviteeIds());
    return new RestResponse<>(ChatData.from(chatInfo));
  }

  @PostMapping("/{chatId}/leave")
  public RestResponse<ChatData> leaveChat(Client client,
      @PathVariable("chatId") String chatId) {
    ChatInfo chatInfo = chatService.leaveChat(client.userId(), chatId);
    return new RestResponse<>(ChatData.from(chatInfo));
  }

  @GetMapping(value = { "/", "" })
  public RestResponse<List<ChatData>> retrieveChat(Client client,
      @RequestParam(name = "updatedAfter") Date updatedAfter) {
    List<ChatDetailInfo> chatInfos = chatService.retrieveChat(client.userId(), updatedAfter);
    return new RestResponse<>(chatInfos.stream().map(ChatData::from).toList());
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    chatService.registerMessageSession(new WSChatSession(session));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    chatService.deregisterMessageSession(session.getId());
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    Client client = Client.from(session.getHandshakeHeaders());
    WSChatEvent chatEvent = WSChatEvent.parse(message.getPayload());
    log.info("Receive: {}", chatEvent);

    switch (chatEvent.getType()) {
      case CREATE_MESSAGE -> handleCreateMessageEvent(client, (WSCreateMessageEvent) chatEvent);
      case READ_MESSAGE -> handleReadMessageEvent(client, (WSReadMessageEvent) chatEvent);
      default -> throw new RuntimeException("Invalid ChatEvent - not supported event");
    }
  }

  private void handleCreateMessageEvent(Client client, WSCreateMessageEvent event) {
    MessageRequest messageRequest = switch (event.getMessage().type()) {
      case TEXT -> MessageRequest.newTextMessageRequest(event.getMessage().text());
      case MEDIA -> MessageRequest.newMediaMessageRequest(event.getMessage().media());
    };
    chatService.addMessage(client.userId(), event.getChatId(), messageRequest);
  }
  
  private void handleReadMessageEvent(Client client, WSReadMessageEvent event) {
    chatService.readMessage(client.userId(), event.getChatId(), event.getLastMessageId());
  }

}
