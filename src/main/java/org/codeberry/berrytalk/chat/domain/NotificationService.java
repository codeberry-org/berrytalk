package org.codeberry.berrytalk.chat.domain;

import java.util.Collection;
import java.util.Set;

public interface NotificationService {
  Set<String> notifyMessage(Collection<String> userIds, Message message);
}
