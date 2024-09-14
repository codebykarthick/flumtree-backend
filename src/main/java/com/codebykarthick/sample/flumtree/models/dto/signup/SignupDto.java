package com.codebykarthick.sample.flumtree.models.dto.signup;

import com.codebykarthick.sample.flumtree.models.entities.User;

public record SignupDto(
    String email,
    String password,
    String confirmPassword,
    String countryCode,
    String phoneNumber,
    String city,
    String state,
    String country) {

  public User createUser() {
    User newUser = new User();
    newUser.setEmail(this.email);
    newUser.setPassword(this.password);
    newUser.setCountryCode(this.countryCode);
    newUser.setPhone(this.phoneNumber);
    newUser.setCity(this.city);
    newUser.setState(this.state);
    newUser.setCountry(this.country);

    return newUser;
  }
}
