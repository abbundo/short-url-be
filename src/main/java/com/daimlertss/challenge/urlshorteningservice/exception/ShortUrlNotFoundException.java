package com.daimlertss.challenge.urlshorteningservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

@ResponseStatus (value = HttpStatus.NOT_FOUND)
public class ShortUrlNotFoundException extends HttpStatusCodeException {

  public ShortUrlNotFoundException(String msg) {
    super(HttpStatus.NOT_FOUND, msg);
  }
}
