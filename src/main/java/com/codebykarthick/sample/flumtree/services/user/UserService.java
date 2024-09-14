package com.codebykarthick.sample.flumtree.services.user;

import com.codebykarthick.sample.flumtree.constants.Errors;
import com.codebykarthick.sample.flumtree.models.dto.login.LoginDto;
import com.codebykarthick.sample.flumtree.models.dto.login.LoginResponseDto;
import com.codebykarthick.sample.flumtree.models.dto.signup.SignupDto;
import com.codebykarthick.sample.flumtree.models.dto.signup.SignupResponseDto;
import com.codebykarthick.sample.flumtree.models.entities.User;
import com.codebykarthick.sample.flumtree.repositories.user.UserRepository;
import com.codebykarthick.sample.flumtree.services.util.JwtService;
import com.codebykarthick.sample.flumtree.services.util.SecurityService;
import com.codebykarthick.sample.flumtree.services.util.ValidatorService;
import java.util.ArrayList;
import java.util.List;
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
  @Autowired ValidatorService validatorService;

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

  /**
   * Method to validate the user input values and if good, save the user in the repository, for
   * subsequent logins.
   *
   * @param signupDto The input data received from user.
   * @return If the input has any errors and if yes, the list of errors as well.
   */
  public ResponseEntity<SignupResponseDto> signupUser(SignupDto signupDto) {
    boolean hasErrors = false;
    List<String> errorList = new ArrayList<>();

    // Validate email
    if (!validatorService.isEmailValid(signupDto.email())) {
      hasErrors = true;
      errorList.add(Errors.EMAIL_INVALID);
    }

    // Validate password
    if (!validatorService.isPasswordStrong(signupDto.password())) {
      hasErrors = true;
      errorList.add(Errors.PASSWORD_WEAK);
    }

    // Compare password and confirm-password
    if (!validatorService.isPasswordAndConfirmPasswordMatching(
        signupDto.password(), signupDto.confirmPassword())) {
      hasErrors = true;
      errorList.add(Errors.PASSWORD_NOT_MATCH);
    }

    // Validate Country code
    if (!validatorService.isCountryCodeValid(signupDto.countryCode())) {
      hasErrors = true;
      errorList.add(Errors.COUNTRY_CODE_INVALID);
    }

    // Validate Phone number
    if (!validatorService.isPhoneNumberValid(signupDto.phoneNumber())) {
      hasErrors = true;
      errorList.add(Errors.PHONE_NUMBER_INVALID);
    }

    // Validate city
    if (validatorService.isPlaceInvalid(signupDto.city())) {
      hasErrors = true;
      errorList.add(Errors.CITY_INVALID);
    }

    // Validate state
    if (validatorService.isPlaceInvalid(signupDto.state())) {
      hasErrors = true;
      errorList.add(Errors.STATE_INVALID);
    }

    // Validate country
    if (validatorService.isPlaceInvalid(signupDto.country())) {
      hasErrors = true;
      errorList.add(Errors.COUNTRY_INVALID);
    }

    log.info("Validation status: {}, error list: {}", hasErrors, errorList);

    if (!hasErrors) {
      // Create the user entity as all validations are successful.
      User newUser = signupDto.createUser();
      // Salt and hash the plaintext password.
      newUser.setPassword(securityService.saltAndHashPassword(newUser.getPassword()));

      // Save the user to database.
      userRepository.save(newUser);
      log.info("User saved to database successfully!");
    }

    SignupResponseDto responseDto = new SignupResponseDto(hasErrors, errorList);
    HttpStatus responseStatus = (hasErrors) ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
    return new ResponseEntity<>(responseDto, responseStatus);
  }
}
