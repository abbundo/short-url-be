package com.daimlertss.challenge.urlshorteningservice;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class UrlShorteningServiceApp {

  public static void main(String[] args) {
    SpringApplication.run(UrlShorteningServiceApp.class, args);
  }

  @Bean
  public JsonMapper createObjectMapper() {
    return JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .disable(WRITE_DATES_AS_TIMESTAMPS)
        .build();
  }
}
