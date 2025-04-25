package org.codeberry.berrytalk.common.config;

import java.util.Collection;

import org.codeberry.berrytalk.common.ws.WebSocketMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {

  private final ApplicationContext applicationContext;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    Collection<WebSocketHandler> handlers = applicationContext.getBeansOfType(WebSocketHandler.class).values();

    for (WebSocketHandler handler : handlers) {
      WebSocketMapping mapping = handler.getClass().getAnnotation(WebSocketMapping.class);
      if (mapping != null) {
        log.info("WebSocket mapping: {} -> {}", handler.getClass().getSimpleName(), mapping.value());
        registry.addHandler(handler, mapping.value());
      }
    }
  }

}
