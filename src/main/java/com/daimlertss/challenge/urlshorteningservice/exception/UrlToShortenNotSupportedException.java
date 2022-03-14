package com.daimlertss.challenge.urlshorteningservice.exception;

import static com.daimlertss.challenge.urlshorteningservice.exception.ShorteningRequestStatus.buildErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

@ResponseStatus (value = HttpStatus.UNPROCESSABLE_ENTITY)
public class UrlToShortenNotSupportedException extends HttpStatusCodeException {

  public UrlToShortenNotSupportedException(String msg) {
    super(HttpStatus.UNPROCESSABLE_ENTITY,
        buildErrorMessage(String.format(
                "The provided protocol '%s' not supported. Only HTTP(S) protocols supported.", msg),
            "002"));
  }
}
