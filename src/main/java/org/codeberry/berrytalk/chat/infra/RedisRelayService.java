package org.codeberry.berrytalk.chat.infra;

import java.util.Collection;
import java.util.Set;

import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.chat.domain.RelayService;
import org.springframework.stereotype.Component;

@Component
public class RedisRelayService implements RelayService {

  @Override
  public Set<String> relayMessage(Collection<String> userIds, Message message) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'relayMessage'");
  }
  
}
