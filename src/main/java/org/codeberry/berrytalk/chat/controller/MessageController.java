package org.codeberry.berrytalk.chat.controller;

import org.codeberry.berrytalk.chat.app.MessageService;
import org.codeberry.berrytalk.chat.app.dto.MessageRequest;
import org.codeberry.berrytalk.chat.controller.dto.AddMessage;
import org.codeberry.berrytalk.chat.controller.dto.Client;
import org.codeberry.berrytalk.common.util.JsonUtil;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController extends TextWebSocketHandler {
  private final MessageService messageService;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    messageService.registerMessageSession(new WebSocketMessageSession(session));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    messageService.deregisterMessageSession(session.getId());
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    Client client = Client.from(session.getHandshakeHeaders());
    AddMessage addMessage = JsonUtil.fromJson(message.getPayload(), AddMessage.class);

    messageService.addMessage(client.userId(), addMessage.chatId(), createMessageRequest(addMessage));
  }

  private MessageRequest createMessageRequest(AddMessage addMessage) {
    return switch(addMessage.type()) {
      case TEXT -> MessageRequest.newTextMessageRequest(addMessage.text());
      case MEDIA -> MessageRequest.newMediaMessageRequest(addMessage.media());
    };
  }

}
