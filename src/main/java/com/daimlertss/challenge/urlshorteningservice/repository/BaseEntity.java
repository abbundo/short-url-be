package com.daimlertss.challenge.urlshorteningservice.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Getter;

@Entity
@Getter
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity {
  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE)
  private Long id;
}
