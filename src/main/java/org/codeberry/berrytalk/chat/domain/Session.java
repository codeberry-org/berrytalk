package org.codeberry.berrytalk.chat.domain;

import java.util.function.Consumer;

import org.codeberry.berrytalk.common.model.DeviceType;

public interface Session {
  String getUserId();
  String getDeviceId();
  DeviceType getDeviceType();
  void sendMessage(Message message);
  void onClose(Consumer<Session> doOnClose);
  void close();
}
