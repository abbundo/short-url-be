package com.daimlertss.challenge.urlshorteningservice.exception;

import static com.daimlertss.challenge.urlshorteningservice.exception.ShorteningRequestStatus.buildErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

@ResponseStatus (value = HttpStatus.CONFLICT)
public class ShortUrlNotCustomUrlException extends HttpStatusCodeException {
  public ShortUrlNotCustomUrlException(String urlToShorten, String shortUrl) {
    super(HttpStatus.CONFLICT, buildErrorMessage(String.format(
        "The provided short URL %s is already associated to the URL %s,"
            + " but not as a Custom URL. Changing type not supported",
        shortUrl, urlToShorten), "003"));
  }
}
