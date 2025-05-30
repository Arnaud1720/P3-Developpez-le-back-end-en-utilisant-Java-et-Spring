package com.arnaud.p3.ChaTop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor  @AllArgsConstructor

  public class Role {
    @GeneratedValue
    @Id
    private Integer id;
    private String name;
  }
