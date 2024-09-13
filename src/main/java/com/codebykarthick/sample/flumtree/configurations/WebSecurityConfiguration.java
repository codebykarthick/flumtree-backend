package com.codebykarthick.sample.flumtree.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    // We do not need csrf as we intended this to just be a REST server for our mobile app.
    httpSecurity
        .authorizeHttpRequests(
            requests ->
                requests
                    // Permit all swagger-ui related endpoints from springdoc
                    // without any authentication
                    .requestMatchers("/swagger-ui/*", "/v3/api-docs", "/v3/api-docs/*")
                    .permitAll()
                    // Permit all public endpoints and h2 database related endpoints
                    // without authentication
                    .requestMatchers(
                        "*/public/*", "/h2-console", "/h2-console/*", "/actuator", "/actuator/*")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .headers(
            customizer -> customizer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
        .csrf(AbstractHttpConfigurer::disable);

    return httpSecurity.build();
  }
}
