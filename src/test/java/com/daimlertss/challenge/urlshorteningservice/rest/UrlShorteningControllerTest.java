package com.daimlertss.challenge.urlshorteningservice.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class UrlShorteningControllerTest extends AbstractShortUrlTest {

  @Test
  void testPostShortenedUrl_url_not_valid() throws Exception {
    mvc.perform(
            post(BASE_URL.concat("/shortening/")).param("urlToShorten", "something senseless"))
        .andExpect(status().isPreconditionFailed());
  }

  @Test
  void testPostShortenedUrl_protocol_not_supported() throws Exception {
    mvc.perform(
            post(BASE_URL.concat("/shortening/")).param("urlToShorten", "ftp://something.com/url/path"))
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void testPostShortenedUrl_Ok() throws Exception {
    mvc.perform(
            post(BASE_URL.concat("/shortening/")).param("urlToShorten",
                "http://something.com/url/path"))
        .andExpect(status().isOk())
        .andExpect(
            content().string(Matchers.containsString("Short url newly generated and persisted")));
    //perform it twice to check if the second time is loaded from DB
    mvc.perform(
            post(BASE_URL.concat("/shortening/")).param("urlToShorten",
                "http://something.com/url/path"))
        .andExpect(status().isOk())
        .andExpect(
            content().string(
                Matchers.containsString("Short url already generated. Loaded from DB.")));
  }
}
