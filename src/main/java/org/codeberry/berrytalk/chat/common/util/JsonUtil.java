package org.codeberry.berrytalk.chat.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
  private static ObjectMapper om = new ObjectMapper();

  private JsonUtil() {}

  public static String toJson(Object obj) {
    try {
      return om.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJson(String json, Class<T> clazz) {
    try {
      return om.readValue(json, clazz);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJson(String json, TypeReference<T> typeRef) {
    try {
      return om.readValue(json, typeRef);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
