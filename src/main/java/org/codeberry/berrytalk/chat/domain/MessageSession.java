package org.codeberry.berrytalk.chat.domain;

import org.codeberry.berrytalk.common.model.DeviceType;

public interface MessageSession {
  String getId();
  String getUserId();
  DeviceType getDeviceType();
  String getDeviceId();
  void sendMessage(Message message);
  void close();
}
