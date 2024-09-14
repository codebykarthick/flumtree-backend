package com.codebykarthick.sample.flumtree.controllers.unauthenticated;

import com.codebykarthick.sample.flumtree.models.dto.login.LoginDto;
import com.codebykarthick.sample.flumtree.models.dto.login.LoginResponseDto;
import com.codebykarthick.sample.flumtree.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
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
@SecurityRequirement(name = "")
@RequestMapping("/v1/public")
@RestController
@Tag(name = "Login Controller", description = "API for logging in users")
public class LoginController {
  @Autowired UserService userService;

  @ApiResponses(
      value = {
        @ApiResponse(
            description = "Returns the JWT token for valid user.",
            responseCode = "200",
            headers = {
              @Header(
                  name = "Authorization",
                  description = "The JWT bearer to be used for other APIs.")
            }),
        @ApiResponse(description = "Returns 404 if the user is not found.", responseCode = "404"),
        @ApiResponse(description = "Returns 401 if the credentials are wrong", responseCode = "401")
      })
  @Operation(
      summary = "Login user",
      description = "Endpoint to login valid users and generate JWT.")
  @PostMapping(value = "/loginUser", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginDto loginDto) {
    return userService.loginUser(loginDto);
  }
}
