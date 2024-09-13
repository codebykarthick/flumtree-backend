package com.codebykarthick.sample.flumtree.services.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SecurityService {
  @Autowired SecureRandom secureRandom;

  @Autowired
  @Qualifier("SHA-512")
  MessageDigest messageDigest;

  /**
   * Function that returns the salted and hashed password ready for inserting into database, using
   * SHA-512 as example.
   *
   * @param plainTextPassword The plaintext password sent by user.
   * @return The salted and hashed password.
   */
  public String saltAndHashPassword(String plainTextPassword) {
    // I use SHA-512 here through the bean, you are welcome to modify.
    byte[] salt = new byte[16];
    secureRandom.nextBytes(salt);
    messageDigest.update(salt);

    byte[] hashedPassword =
        messageDigest.digest(plainTextPassword.getBytes(StandardCharsets.UTF_8));
    // Concatenate the salt and the hashed password.
    byte[] saltAndHashedPassword = ArrayUtils.addAll(salt, hashedPassword);

    // Create a base64 string for easy storage.
    return Base64.getEncoder().encodeToString(saltAndHashedPassword);
  }

  /**
   * Utility method to compare a plainText password that user sends during login with that of the
   * salted password that is present in our database.
   *
   * @param plainTextPassword The plain unsalted password that user sends during login.
   * @param saltedPassword The salted password that is retrieved for the email id.
   * @return true if the values are the same, false if they are not.
   */
  public boolean comparePasswordWithSaltedValue(String plainTextPassword, String saltedPassword) {
    // Password from database is base64 encoded
    byte[] decodedPassword = Base64.getDecoder().decode(saltedPassword);
    // The first 16 bytes is salt
    byte[] salt = Arrays.copyOfRange(decodedPassword, 0, 16);

    // Salt and digest the plaintext
    messageDigest.update(salt);
    byte[] newHashedPassword =
        messageDigest.digest(plainTextPassword.getBytes(StandardCharsets.UTF_8));

    // Append the salt in the same way as originally done.
    byte[] saltAndNewHashedPassword = ArrayUtils.addAll(salt, newHashedPassword);

    // Both arrays should be same if the passwords are same.
    return Arrays.equals(saltAndNewHashedPassword, decodedPassword);
  }
}
