package com.daimlertss.challenge.urlshorteningservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrlEntity, Long> {

  ShortenedUrlEntity findShortenedUrlEntityByOriginalUrl(String originalUrl);

  ShortenedUrlEntity findShortenedUrlEntityByShortUrl(String shortUrl);
}
