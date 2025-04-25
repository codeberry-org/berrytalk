package org.codeberry.berrytalk.chat.controller.dto;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.codeberry.berrytalk.common.model.DeviceType;

public record Client(
    String userId,
    DeviceType deviceType,
    String deviceId) {
  private static final String HEADER_USER_ID = "x-user-id";
  private static final String HEADER_DEVICE_TYPE = "x-device-type";
  private static final String HEADER_DEVICE_ID = "x-device-id";

  public static Client from(Map<String, List<String>> headers) {
    return new Client(
        findFirstHeader(headers, HEADER_USER_ID),
        DeviceType.valueOf(findFirstHeader(headers, HEADER_DEVICE_TYPE)),
        findFirstHeader(headers, HEADER_DEVICE_ID));
  }

  private static String findFirstHeader(Map<String, List<String>> headers, String header) {
    List<String> values = headers.getOrDefault(header, Collections.emptyList());
    return values.isEmpty() ? null : values.get(0);
  }
}
