package com.daimlertss.challenge.urlshorteningservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortUrl {
  String originalUrl;
  String shortUrl;
  String message;
}
