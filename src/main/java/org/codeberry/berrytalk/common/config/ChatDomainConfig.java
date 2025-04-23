package org.codeberry.berrytalk.common.config;

import org.codeberry.berrytalk.chat.domain.MessageSessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatDomainConfig {
  
  @Bean
  public MessageSessionService sessionManager() {
    return new MessageSessionService();
  }
}
