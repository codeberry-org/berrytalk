package org.codeberry.berrytalk.common.util;

import java.util.UUID;

public class IdUtil {
  
  private IdUtil() {}

  public static String create(String prefix) {
    return String.format("%s-%s", prefix, UUID.randomUUID().toString());
  }
}
