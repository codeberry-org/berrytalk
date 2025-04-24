package org.codeberry.berrytalk.chat.infra;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.codeberry.berrytalk.chat.domain.RelayService;
import org.codeberry.berrytalk.chat.domain.event.ChatEvent;
import org.springframework.stereotype.Component;

@Component
public class RedisRelayService implements RelayService {

  @Override
  public Set<String> relayChatEvent(Collection<String> userIds, ChatEvent event) {
    // TODO
    return new HashSet<>(userIds);
  }
  
}
