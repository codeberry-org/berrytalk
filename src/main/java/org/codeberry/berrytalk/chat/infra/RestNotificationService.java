package org.codeberry.berrytalk.chat.infra;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.codeberry.berrytalk.chat.domain.NotificationService;
import org.codeberry.berrytalk.chat.domain.event.ChatEvent;

public class RestNotificationService implements NotificationService {

  @Override
  public Set<String> notifyChatEvent(Collection<String> userIds, ChatEvent event) {
    // TODO
    return new HashSet<>(userIds);
  }
  
}
