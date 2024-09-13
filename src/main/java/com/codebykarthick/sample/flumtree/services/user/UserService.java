package com.codebykarthick.sample.flumtree.services.user;

import com.codebykarthick.sample.flumtree.models.dto.login.LoginDto;
import com.codebykarthick.sample.flumtree.models.dto.login.LoginResponseDto;
import com.codebykarthick.sample.flumtree.models.entities.User;
import com.codebykarthick.sample.flumtree.repositories.user.UserRepository;
import com.codebykarthick.sample.flumtree.services.util.JwtService;
import com.codebykarthick.sample.flumtree.services.util.SecurityService;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {
  @Autowired JwtService jwtService;
  @Autowired SecurityService securityService;
  @Autowired UserRepository userRepository;

  /**
   * Method to check if the user corresponding to the provided email exists and if the passwords
   * match to ensure the user has authenticated successfully.
   *
   * @param loginDto The details for login from user.
   * @return The same email back with the signed JWT for post login authentication.
   */
  public ResponseEntity<LoginResponseDto> loginUser(LoginDto loginDto) {
    Optional<User> user = userRepository.findByEmail(loginDto.email());

    // Ideally we should return 401, for both cases of email not present and
    // wrong credentials to prevent harvest of data but this is done just to show the possibility
    if (user.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    User fetchedUser = user.get();

    log.info("Fetched user: {}", fetchedUser);

    if (!securityService.comparePasswordWithSaltedValue(
        loginDto.password(), fetchedUser.getPassword())) {
      log.info("User credentials did not match!");
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    log.info("Generating JWT for the user");

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setBearerAuth(jwtService.generateJwtSigned(loginDto.email()));

    return new ResponseEntity<>(
        new LoginResponseDto(loginDto.email()), responseHeaders, HttpStatus.OK);
  }
}
