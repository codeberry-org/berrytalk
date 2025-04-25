package org.codeberry.berrytalk.chat.controller;

import java.io.IOException;

import org.codeberry.berrytalk.chat.controller.dto.Client;
import org.codeberry.berrytalk.chat.controller.dto.event.WSChatEvent;
import org.codeberry.berrytalk.chat.domain.event.ChatEvent;
import org.codeberry.berrytalk.chat.domain.ChatSession;
import org.codeberry.berrytalk.common.model.DeviceType;
import org.codeberry.berrytalk.common.util.JsonUtil;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WSChatSession implements ChatSession {
  private final String id;
  private final String userId;
  private final DeviceType deviceType;
  private final String deviceId;
  private final WebSocketSession webSocketSession;

  WSChatSession(WebSocketSession webSocketSession) {
    Client client = Client.from(webSocketSession.getHandshakeHeaders());
    this.id = webSocketSession.getId();
    this.userId = client.userId();
    this.deviceType = client.deviceType();
    this.deviceId = client.deviceId();
    this.webSocketSession = webSocketSession;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getUserId() {
    return userId;
  }

  @Override
  public DeviceType getDeviceType() {
    return deviceType;
  }

  @Override
  public String getDeviceId() {
    return deviceId;
  }

  @Override
  public void sendChatEvent(ChatEvent chatEvent) {
    log.info("Send: {}", chatEvent);
    TextMessage textMessage = new TextMessage(JsonUtil.toJson(WSChatEvent.from(chatEvent)));
    try {
      webSocketSession.sendMessage(textMessage);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() {
    try {
      webSocketSession.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
}
