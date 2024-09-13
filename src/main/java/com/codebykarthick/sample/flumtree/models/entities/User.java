package com.codebykarthick.sample.flumtree.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

// H2 also has a table called USERS which will conflict, hence the name change.
@Entity(name = "users")
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String email;
  private String password;
  private String countryCode;
  private String phone;
  private String city;
  private String state;
  private String country;
}
