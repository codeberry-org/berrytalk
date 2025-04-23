package org.codeberry.berrytalk.common.config;

import org.codeberry.berrytalk.chat.domain.SessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
  
  @Bean
  public SessionManager sessionManager() {
    return new SessionManager();
  }
}
