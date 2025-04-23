package org.codeberry.berrytalk.chat.controller.dto;

import java.util.List;

import org.codeberry.berrytalk.common.model.DeviceType;
import org.springframework.http.HttpHeaders;

public record Client(
    String userId,
    DeviceType deviceType,
    String deviceId) {
  private static final String HEADER_USER_ID = "x-user-id";
  private static final String HEADER_DEVICE_TYPE = "x-device-type";
  private static final String HEADER_DEVICE_ID = "x-device-id";

  public static Client from(HttpHeaders headers) {
    return new Client(
        findFirstHeader(headers, HEADER_USER_ID),
        DeviceType.valueOf(findFirstHeader(headers, HEADER_DEVICE_TYPE)),
        findFirstHeader(headers, HEADER_DEVICE_ID));
  }

  private static String findFirstHeader(HttpHeaders headers, String header) {
    List<String> values = headers.getOrEmpty(header);
    return values.isEmpty() ? null : values.get(0);
  }
}
