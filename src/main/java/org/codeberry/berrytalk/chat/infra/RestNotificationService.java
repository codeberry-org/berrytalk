package org.codeberry.berrytalk.chat.infra;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.chat.domain.NotificationService;

public class RestNotificationService implements NotificationService {

  @Override
  public Set<String> notifyMessage(Collection<String> userIds, Message message) {
    // TODO
    return new HashSet<>(userIds);
  }
  
}
