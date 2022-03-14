package com.daimlertss.challenge.urlshorteningservice.exception;

import static com.daimlertss.challenge.urlshorteningservice.exception.ShorteningRequestStatus.buildErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

@ResponseStatus (value = HttpStatus.PRECONDITION_FAILED)
public class UrlToShortenNotValidException extends HttpStatusCodeException {

  public UrlToShortenNotValidException(String msg) {
    super(HttpStatus.PRECONDITION_FAILED,
        buildErrorMessage(String.format("The provided URL is not valid: '%s'", msg), "001"));
  }
}
