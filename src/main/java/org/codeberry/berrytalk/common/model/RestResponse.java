package org.codeberry.berrytalk.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {
  private final String code;
  private final String message;
  private final T data;

  public RestResponse(T data) {
    this.code = "0000";
    this.message = "Success";
    this.data = data;
  }

  public RestResponse(String code, String message) {
    this.code = code;
    this.message = message;
    this.data = null;
  }
}
