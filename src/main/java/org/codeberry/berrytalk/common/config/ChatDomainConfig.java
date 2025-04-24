package org.codeberry.berrytalk.common.config;

import org.codeberry.berrytalk.chat.domain.ChatSessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatDomainConfig {
  
  @Bean
  public ChatSessionService sessionManager() {
    return new ChatSessionService();
  }
}
