package org.codeberry.berrytalk.common.util;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
  private static ObjectMapper om = new ObjectMapper();
  static {
    om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

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

  public static Optional<String> findStringField(String json, String field) {
    try {
      JsonNode root = om.readTree(json);
      return root.findValuesAsText(field).stream().findFirst();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
