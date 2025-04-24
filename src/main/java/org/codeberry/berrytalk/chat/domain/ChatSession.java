package org.codeberry.berrytalk.chat.domain;

import org.codeberry.berrytalk.chat.domain.event.ChatEvent;
import org.codeberry.berrytalk.common.model.DeviceType;

public interface ChatSession {
  String getId();
  String getUserId();
  DeviceType getDeviceType();
  String getDeviceId();
  void sendChatEvent(ChatEvent event);
  void close();
}
