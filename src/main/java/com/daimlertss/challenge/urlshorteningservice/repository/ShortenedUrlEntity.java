package com.daimlertss.challenge.urlshorteningservice.repository;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "shortened_url", indexes = @Index (columnList = "original_url, short_url"))
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ShortenedUrlEntity extends BaseEntity {

  @NotBlank
  @Valid
  @Column (name = "original_url")
  private String originalUrl;

  @NotBlank
  @Valid
  @Column (name = "short_url")
  private String shortUrl;

  @NotNull
  @Valid
  @Column (name = "is_custom_url")
  private boolean customUrl;
}
