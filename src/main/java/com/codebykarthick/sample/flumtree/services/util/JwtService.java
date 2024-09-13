package com.codebykarthick.sample.flumtree.services.util;

import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class JwtService {
  @Autowired SecretKey secretKey;

  /**
   * Method to generate the signed JWT for the user email as the subject
   *
   * @param userEmail The user's email to be used as the subject for the JWT
   * @return The compacted form of the signed JWT as a string.
   */
  public String generateJwtSigned(String userEmail) {
    // As of now I am just minting a token with the user's email and only signing.
    // If encryption is also implemented this can have the roles the user can have.
    return Jwts.builder().subject(userEmail).signWith(secretKey).compact();
  }

  /**
   * Method to validate the provided string form of the jwt.
   *
   * @param jwtToValidate The compact string form of the JWT to validate.
   * @return boolean to indicate if the validation is successful or not.
   */
  public boolean isSignedJwtValid(String jwtToValidate) {
    // Exception is thrown by the library if the parsing fails.
    try {
      Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwtToValidate);
    } catch (Exception e) {
      log.error("Error occurred while parsing signed jwt.", e);
      return false;
    }

    // If it is successful this will be returned.
    return true;
  }
}
