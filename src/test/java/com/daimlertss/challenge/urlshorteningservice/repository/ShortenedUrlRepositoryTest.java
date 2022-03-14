package com.daimlertss.challenge.urlshorteningservice.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
class ShortenedUrlRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ShortenedUrlRepository repository;

  @Test
  public void testViolationConstrain_fail_on_load() {
    ShortenedUrlEntity shortenedUrlEntity =
        ShortenedUrlEntity.builder().originalUrl("").shortUrl("").customUrl(false).build();
    entityManager.persist(shortenedUrlEntity);
    assertThrows(ConstraintViolationException.class, () -> repository.findAll());
  }

}
