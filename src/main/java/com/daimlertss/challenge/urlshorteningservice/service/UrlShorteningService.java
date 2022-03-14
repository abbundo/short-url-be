package com.daimlertss.challenge.urlshorteningservice.service;

import com.daimlertss.challenge.urlshorteningservice.exception.ShortUrlNotCustomUrlException;
import com.daimlertss.challenge.urlshorteningservice.exception.ShortUrlNotFoundException;
import com.daimlertss.challenge.urlshorteningservice.exception.UrlToShortenNotSupportedException;
import com.daimlertss.challenge.urlshorteningservice.exception.UrlToShortenNotValidException;
import com.daimlertss.challenge.urlshorteningservice.model.ShortUrl;
import com.daimlertss.challenge.urlshorteningservice.repository.ShortenedUrlEntity;
import com.daimlertss.challenge.urlshorteningservice.repository.ShortenedUrlRepository;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlShorteningService {

  final ShortenedUrlRepository shortenedUrlRepository;

  /**
   * Validate the input String as a valid URL
   * Then look in DB if the provided URL was already shortened,
   * otherwise proceed to shortening and persisting it.
   * The shortened URL object is returned
   *
   * @param urlToShorten the input UR provided as a String
   * @return the shortened URL
   * @see ShortUrl
   */
  public ShortUrl validateAndShorten(String urlToShorten) {
    log.debug("validateAndShorten received: {}", urlToShorten);
    try {
      URL incomingUrl = new URL(urlToShorten);
      String[] hostParts = validateUrlAndReturnHostParts(incomingUrl);
      //look into DB if a shortUrl is already associated
      ShortenedUrlEntity urlEntity =
          shortenedUrlRepository.findShortenedUrlEntityByOriginalUrl(urlToShorten);
      ShortUrl shortenedUrl;
      if (urlEntity != null) {//if yes then return this one
        shortenedUrl = ShortUrl.builder()
            .originalUrl(urlEntity.getOriginalUrl())
            .shortUrl(urlEntity.getShortUrl())
            .message("Short url already generated. Loaded from DB.")
            .build();
      } else {//Otherwise, build a new Tiny Url
        String hash = RandomStringUtils.random(6, true, true);
        //[www].domainname.tld -> getting the "domainname" part
        String newHost = hostParts[hostParts.length - 2];
        int length = newHost.length();
        if (length > 2) {//if domainnamepart is longer then 2 chars, then split it
          newHost = newHost.substring(0, length - 2) + "." + newHost.substring(length - 2);
        }
        shortenedUrl = ShortUrl.builder()
            .originalUrl(urlToShorten)
            .shortUrl(new URL(incomingUrl.getProtocol() + "://" + newHost + "/" + hash).toString())
            .message("Short url newly generated and persisted.")
            .build();
        urlEntity = ShortenedUrlEntity.builder()
            .shortUrl(shortenedUrl.getShortUrl())
            .originalUrl(shortenedUrl.getOriginalUrl())
            .customUrl(false)
            .build();
        shortenedUrlRepository.save(urlEntity);
      }
      return shortenedUrl;
    } catch (MalformedURLException e) {
      throw new UrlToShortenNotValidException(e.getMessage());
    }
  }

  /**
   * Look for the original URL associated to a given shortUrl
   *
   * @param shortUrl the shortUrl to search for
   * @return the shortUrl object
   * @see ShortUrl
   */
  public ShortUrl getOriginalUrl(String shortUrl) {
    log.debug("getOriginalUrl received: {}", shortUrl);
    ShortenedUrlEntity urlEntity =
        shortenedUrlRepository.findShortenedUrlEntityByShortUrl(shortUrl);
    if (urlEntity != null) {
      return ShortUrl.builder()
          .originalUrl(urlEntity.getOriginalUrl())
          .shortUrl(urlEntity.getShortUrl())
          .message("Short url found and loaded from DB.")
          .build();
    } else {
      throw new ShortUrlNotFoundException(
          String.format("The provided short URL not found: '%s'", shortUrl));
    }
  }

  /**
   * Customize the association between a given URL and its shortUrl
   *
   * @param urlToShorten the original URL
   * @param shortUrl     the short URL to associate
   * @return the ShortUrl object
   * @see ShortUrl
   */
  public ShortUrl validateAndCustomize(String urlToShorten, String shortUrl) {
    log.debug("validateAndCustomize received: {} {}", urlToShorten, shortUrl);
    try {
      //Validate both URLs before proceeding
      validateUrlAndReturnHostParts(new URL(urlToShorten));
      validateUrlAndReturnHostParts(new URL(shortUrl));
      //check in the DB if the association already exists
      ShortenedUrlEntity urlEntity = shortenedUrlRepository
          .findShortenedUrlEntityByOriginalUrl(urlToShorten);
      if (urlEntity != null) {
        //if the association exists then check if it was a Custom One
        if (urlEntity.isCustomUrl()) {
          return ShortUrl.builder()
              .originalUrl(urlEntity.getOriginalUrl())
              .shortUrl(urlEntity.getShortUrl())
              .message("Short url already generated. Loaded from DB.")
              .build();
        } else {
          //throw an exception since the required association exists but is not custom
          throw new ShortUrlNotCustomUrlException(urlToShorten, shortUrl);
        }
      } else {
        urlEntity = ShortenedUrlEntity.builder()
            .shortUrl(shortUrl)
            .originalUrl(urlToShorten)
            .customUrl(true)
            .build();
        shortenedUrlRepository.save(urlEntity);
        return ShortUrl.builder()
            .originalUrl(urlToShorten)
            .shortUrl(shortUrl)
            .message("Short url associated to the given URL. Information persisted.")
            .build();
      }
    } catch (MalformedURLException e) {
      throw new UrlToShortenNotValidException(e.getMessage());
    }
  }

  /**
   * Validate a given URL object and return ist hostname split by dots
   * Example: https://www.domain.com, will return ["www","domain","com"]
   *
   * @param incomingUrl the URL object to check
   * @return the split hostname
   */
  private String[] validateUrlAndReturnHostParts(URL incomingUrl) {
    if (!List.of("http", "https").contains(incomingUrl.getProtocol().toLowerCase())) {
      throw new UrlToShortenNotSupportedException(incomingUrl.getProtocol());
    }
    String[] hostParts = incomingUrl.getHost().split("\\.");
    if (hostParts.length < 2) {
      throw new UrlToShortenNotValidException(incomingUrl + "  Url without TLD not supported.");
    }
    return hostParts;
  }

  /**
   * Load all shortUrls saved in DB and return them in a List
   *
   * @return List of all ShortUrls
   */
  public List<ShortUrl> getAllShortUrls() {
    List<ShortenedUrlEntity> shortUrls = shortenedUrlRepository.findAll();
    if (CollectionUtils.isEmpty(shortUrls)) {
      throw new ShortUrlNotFoundException("No short URL found in DB.");
    }
    return shortUrls.stream().map(entity -> ShortUrl.builder()
        .originalUrl(entity.getOriginalUrl())
        .shortUrl(entity.getShortUrl())
        .message(entity.isCustomUrl() ? "Custom URL" : "Non custom URL")
        .build()).toList();
  }
}
