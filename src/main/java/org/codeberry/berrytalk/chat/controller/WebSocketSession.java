package org.codeberry.berrytalk.chat.controller;

import java.util.function.Consumer;

import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.chat.domain.Session;
import org.codeberry.berrytalk.common.model.DeviceType;

public class WebSocketSession implements Session {

  @Override
  public String getUserId() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUserId'");
  }

  @Override
  public String getDeviceId() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getDeviceId'");
  }

  @Override
  public DeviceType getDeviceType() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getDeviceType'");
  }

  @Override
  public void sendMessage(Message message) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'sendMessage'");
  }

  @Override
  public void onClose(Consumer<Session> doOnClose) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'onClose'");
  }

  @Override
  public void close() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'close'");
  }
  
}
