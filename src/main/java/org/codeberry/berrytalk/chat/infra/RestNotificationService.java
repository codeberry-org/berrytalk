package org.codeberry.berrytalk.chat.infra;

import java.util.Collection;
import java.util.Set;

import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.chat.domain.NotificationService;

public class RestNotificationService implements NotificationService {

  @Override
  public Set<String> notifyMessage(Collection<String> userIds, Message message) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'notifyMessage'");
  }
  
}
