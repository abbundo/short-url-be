package com.daimlertss.challenge.urlshorteningservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  public RestResponseEntityExceptionHandler() {
    super();
  }

  @ExceptionHandler ({UrlToShortenNotValidException.class, UrlToShortenNotSupportedException.class})
  public ResponseEntity<Object> handleNotDeliveredException(
      final HttpStatusCodeException ex,
      final WebRequest request
  ) {
    return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), ex.getStatusCode(),
        request);
  }
}
