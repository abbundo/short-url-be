package com.daimlertss.challenge.urlshorteningservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest ()
@AutoConfigureMockMvc
abstract class AbstractShortUrlTest {

  static final String BASE_URL = "/tssly";

  @Autowired
  MockMvc mvc;

}
