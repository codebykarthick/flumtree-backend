package com.codebykarthick.sample.flumtree.constants;

public class Errors {
  private Errors() {}

  public static final String EMAIL_INVALID = "Email address is invalid.";
  public static final String PASSWORD_WEAK =
      "Password is weak, should have at 1 uppercase, 1 lowercase, "
          + "1 number, 1 special char and at least 8 characters long.";
  public static final String PASSWORD_NOT_MATCH = "Password and confirm password do not match.";
  public static final String COUNTRY_CODE_INVALID = "Country code entered is invalid.";
  public static final String PHONE_NUMBER_INVALID = "Phone number is invalid.";
  public static final String CITY_INVALID = "City is invalid.";
  public static final String STATE_INVALID = "State is invalid.";
  public static final String COUNTRY_INVALID = "Country is invalid.";
}
