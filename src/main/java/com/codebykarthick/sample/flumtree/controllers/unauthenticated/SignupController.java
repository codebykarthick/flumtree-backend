package com.codebykarthick.sample.flumtree.controllers.unauthenticated;

import com.codebykarthick.sample.flumtree.models.dto.signup.SignupDto;
import com.codebykarthick.sample.flumtree.models.dto.signup.SignupResponseDto;
import com.codebykarthick.sample.flumtree.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/v1/public")
@SecurityRequirement(name = "")
@Tag(name = "Signup Controller", description = "API for creating a new user.")
public class SignupController {
  @Autowired UserService userService;

  @ApiResponses(
      value = {
        @ApiResponse(
            description = "Returns 200 with no errors if the input data is all good.",
            responseCode = "200"),
        @ApiResponse(
            description = "Returns 400 with the list of errors if there is issue with the input",
            responseCode = "400")
      })
  @Operation(
      summary = "Sign up a new user",
      description = "Get the data from a new user, validate it and if good, create the user")
  @PostMapping(value = "/signupUser", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SignupResponseDto> signupUser(@RequestBody SignupDto signupDto) {
    return userService.signupUser(signupDto);
  }
}
