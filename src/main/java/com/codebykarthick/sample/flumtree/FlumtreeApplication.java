package com.codebykarthick.sample.flumtree;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Flumtree Backend",
            description = "Spring Boot REST Backend for Flumtree",
            contact =
                @Contact(
                    name = "Sri Hari Karthick",
                    url = "https://codebykarthick.github.io/about")))
@SpringBootApplication
public class FlumtreeApplication {
  public static void main(String[] args) {
    SpringApplication.run(FlumtreeApplication.class, args);
  }
}
