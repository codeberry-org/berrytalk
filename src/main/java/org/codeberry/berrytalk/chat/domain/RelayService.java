package org.codeberry.berrytalk.chat.domain;

import java.util.Collection;
import java.util.Set;

public interface RelayService {
  Set<String> relayMessage(Collection<String> userIds, Message message);
}
