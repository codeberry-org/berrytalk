package org.codeberry.berrytalk.chat.controller.handler;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codeberry.berrytalk.chat.controller.dto.Client;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ClientInterceptorAndResolver implements HandlerInterceptor, HandlerMethodArgumentResolver {
  private static String ATTR_CLIENT = "client";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    Client client = Client.from(extractHeaders(request));
    request.setAttribute(ATTR_CLIENT, client);
    return true;
  }

  private Map<String, List<String>> extractHeaders(HttpServletRequest request) {
    Map<String, List<String>> headersMap = new HashMap<>();
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      Enumeration<String> headerValues = request.getHeaders(headerName);
      List<String> values = new ArrayList<>();
      while (headerValues.hasMoreElements()) {
        values.add(headerValues.nextElement());
      }
      headersMap.put(headerName, values);
    }
    return headersMap;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(Client.class);
  }

  @Override
  @Nullable
  public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    return request.getAttribute(ATTR_CLIENT);
  }
  
}
