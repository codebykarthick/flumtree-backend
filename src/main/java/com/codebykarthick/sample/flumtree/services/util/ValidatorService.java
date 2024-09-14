package com.codebykarthick.sample.flumtree.services.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {
  static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

  // Password should have 1 lowercase, 1 uppercase, 1 number, 1 special char
  // and is at least 8 chars long.
  static final Pattern STRONG_PASS_PATTERN =
      Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8,}$");

  // Some weird codes have +44-1639
  static final Pattern COUNTRY_CODE_PATTERN = Pattern.compile("^\\+\\d{1,2}(-\\d{3,4})?\\d?$");

  static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");

  static final Pattern PLACE_PATTERN = Pattern.compile("[a-zA-Z-\\s]+");

  /**
   * Method to check if the provided value is a valid email.
   *
   * @param emailAddress The email address to validate.
   * @return true if it is a valid email address, false if it is not.
   */
  public boolean isEmailValid(String emailAddress) {
    Matcher matcher = EMAIL_PATTERN.matcher(emailAddress);
    return matcher.matches();
  }

  /**
   * Method to check if the provided password is strong and has 1 lowercase, 1 uppercase, 1 number,
   * 1 special char and is at least 8 chars long.
   *
   * @param password The password value to check.
   * @return true if it is a strong password and false if it is not.
   */
  public boolean isPasswordStrong(String password) {
    Matcher matcher = STRONG_PASS_PATTERN.matcher(password);
    return matcher.matches();
  }

  /**
   * Method to check if provided password matches with the confirm-password.
   *
   * @param password The password field value.
   * @param confirmPassword The confirm-password field value.
   * @return true if they are equal, false if they are not.
   */
  public boolean isPasswordAndConfirmPasswordMatching(String password, String confirmPassword) {
    return password.equals(confirmPassword);
  }

  /**
   * Method to check if the country code is valid.
   *
   * @param countryCode Value to check if it is a valid country code.
   * @return true if it is a valid country code, false otherwise.
   */
  public boolean isCountryCodeValid(String countryCode) {
    Matcher matcher = COUNTRY_CODE_PATTERN.matcher(countryCode);
    return matcher.matches();
  }

  /**
   * Method to check if the phone number is valid.
   *
   * @param phoneNumber Value to check if it is a valid phone number.
   * @return true if it is a valid phone number, false otherwise.
   */
  public boolean isPhoneNumberValid(String phoneNumber) {
    Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
    return matcher.matches();
  }

  /**
   * Generic method to check if place has only words and hyphen
   *
   * @param place The value to validate
   * @return true if it is an invalid place, false otherwise.
   */
  public boolean isPlaceInvalid(String place) {
    Matcher matcher = PLACE_PATTERN.matcher(place);
    return !matcher.matches();
  }
}
