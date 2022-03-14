package com.daimlertss.challenge.urlshorteningservice.rest;

import com.daimlertss.challenge.urlshorteningservice.model.ShortUrl;
import com.daimlertss.challenge.urlshorteningservice.service.UrlShorteningService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//TODO: Just to allow UI-Testing
@CrossOrigin
@RestController
@RequestMapping ("/tssly")
@RequiredArgsConstructor
@Slf4j
public class UrlShorteningController {

  private final UrlShorteningService urlShorteningService;

  @ApiOperation (value = "Take a URL and return a short URL.", response = ShortUrl.class)
  @ApiResponses (value = {@ApiResponse (code = 200, message = "URL shortened successfully."),
      @ApiResponse (code = 412, message = """
          The provided URL was not valid. Example:
          ```
          {
            "errorCode":"001",
            "errorDescription":"The provided URL 'http://123' is not valid."
          }
          ```
          """), @ApiResponse (code = 422, message = """
      The provided protocol is not supported. Example:
      ```
      {
        "errorCode":"002",
        "errorDescription":"The provided protocol 'ftp' not supported. Only HTTP(S) protocols supported."
      }
      ```
      """),})
  @PostMapping (value = "/shortening")
  public ResponseEntity<ShortUrl> postShortenedUrl(
      @ApiParam (value = "The original URL to be shortened", required = true)
      @RequestParam (value = "urlToShorten") String urlToShorten
  ) {
    log.debug("POST received: " + urlToShorten);
    ShortUrl shortenedUrl = urlShorteningService.validateAndShorten(urlToShorten);
    log.debug("POST returned: " + shortenedUrl.getShortUrl());
    return ResponseEntity.ok(shortenedUrl);
  }

  @ApiOperation (value = "Take a URL and a short URL and bind them.", response = ShortUrl.class)
  @ApiResponses (value = {@ApiResponse (code = 200, message = "Short URL customized successfully."),
      @ApiResponse (code = 412, message = """
          The provided URL was not valid. Example:
          ```
          {
            "errorCode":"001",
            "errorDescription":"The provided URL 'http://123' is not valid."
          }
          ```
          """),
      @ApiResponse (code = 422, message = """
          The provided protocol is not supported. Example:
          ```
          {
            "errorCode":"002",
            "errorDescription":"The provided protocol 'ftp' not supported. Only HTTP(S) protocols supported."
          }
          ```
          """),
      @ApiResponse (code = 409, message = """
          The required operation is not allowed. Example:
          ```
          {
            "errorCode":"003",
            "errorDescription":"The provided short URL http://repubbli.ca/PN9Dbo is already associated to the URL http://repubblica.it/oneday, but not as a Custom URL. Changing type not supported"
          }
          ```
          """),})
  @PostMapping (value = "/customize")
  public ResponseEntity<ShortUrl> postCustomShortenedUrl(
      @ApiParam (value = "The original URL to be shortened", required = true)
      @RequestParam (value = "urlToShorten") String urlToShorten,
      @ApiParam (value = "The short URL to associate", required = true)
      @RequestParam (value = "shortUrl") String shortUrl
  ) {
    log.debug("POST received: " + urlToShorten);
    ShortUrl shortenedUrl = urlShorteningService.validateAndCustomize(urlToShorten, shortUrl);
    log.debug("POST returned: " + shortenedUrl);
    return ResponseEntity.ok(shortenedUrl);
  }

  @ApiOperation (value = "Take a short URL and return the original URL.", response = ShortUrl.class)
  @ApiResponses (value = {@ApiResponse (code = 200, message = "URL retrieved successfully."),
      @ApiResponse (code = 404, message = """
          ```
          {
            "timestamp": "2022-03-10T16:24:22.766+00:00",
            "status": 404,
            "error": "Not Found",
            "message": "404 The provided short URL not found: 'http://myu.rl/12erde'",
            "path": "/tssly/redirect"
          }
          ```
          """),})
  @GetMapping (value = "/redirect")
  public ResponseEntity<ShortUrl> getOriginalUrl(
      @ApiParam (value = "The short URL to search for", required = true)
      @RequestParam (value = "shortUrl") String shortUrl
  ) {
    log.debug("GET received: " + shortUrl);
    ShortUrl shortUrlObject = urlShorteningService.getOriginalUrl(shortUrl);
    log.debug("GET returned: " + shortUrlObject.getOriginalUrl());
    return ResponseEntity.ok(shortUrlObject);
  }

  @ApiOperation (value = "Return all short URLs registered since now. This is for admin purpose only.", response = ShortUrl.class)
  @ApiResponses (value =
      {@ApiResponse (code = 200, message = "URLs retrieved successfully."),
          @ApiResponse (code = 404, message = "No URL found.")
      })
  @GetMapping (value = "/all")
  public ResponseEntity<List<ShortUrl>> getAllShortUrls() {
    List<ShortUrl> shortUrlObject = urlShorteningService.getAllShortUrls();
    log.debug("GET returned: " + shortUrlObject);
    return ResponseEntity.ok(shortUrlObject);
  }
}
