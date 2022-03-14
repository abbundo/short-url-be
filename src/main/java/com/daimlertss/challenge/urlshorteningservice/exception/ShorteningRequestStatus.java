package com.daimlertss.challenge.urlshorteningservice.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Response returned in case of error
 * <pre>
 * {@code
 *     {
 *     "errorCode":"001",
 *     "errorDescription":"The provided URL 'http://123' is not valid."
 *     }
 * }
 * </pre>
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShorteningRequestStatus {
  String errorCode;
  String errorDescription;

  @JsonIgnore
  public static String buildErrorMessage(String msg, String errorCode) {
    try {
      return new JsonMapper().writeValueAsString(
          ShorteningRequestStatus.builder()
              .errorCode(errorCode)
              .errorDescription(msg)
              .build());
    } catch (JsonProcessingException e) {
      return "";
    }
  }
}

