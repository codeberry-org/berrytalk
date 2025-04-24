package org.codeberry.berrytalk.chat.domain;

import java.util.Collection;
import java.util.Set;

import org.codeberry.berrytalk.chat.domain.event.ChatEvent;

public interface RelayService {
  Set<String> relayChatEvent(Collection<String> userIds, ChatEvent event);
}
