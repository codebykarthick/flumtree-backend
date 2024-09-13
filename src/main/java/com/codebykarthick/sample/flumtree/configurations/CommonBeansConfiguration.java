package com.codebykarthick.sample.flumtree.configurations;

import io.jsonwebtoken.Jwts;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
public class CommonBeansConfiguration {

  /**
   * Bean method that creates an instance of secure random for better testability.
   *
   * @return The SecureRandom instance to be autowired in other classes.
   */
  @Bean
  public SecureRandom secureRandom() {
    return new SecureRandom();
  }

  /**
   * Bean method that creates an instance of MessageDigest for SHA-512
   *
   * @return The MessageDigest instance for the SHA-512 algorithm
   * @throws Exception If the JVM does not support SHA-512 algorithm
   */
  @Bean
  @Qualifier("SHA-512")
  @RequestScope
  public MessageDigest messageDigest() throws Exception {
    return MessageDigest.getInstance("SHA-512");
  }

  /**
   * Bean method that provides an instance of RequestContextListener, which Spring uses to expose
   * Request scope beans outside the context of a request, i.e, to allow to use Request Scope beans
   * outside a controller.
   *
   * @return The RequestContextListener instance.
   */
  @Bean
  public RequestContextListener requestContextListener() {
    return new RequestContextListener();
  }

  /**
   * Bean method that provides an instance of SecretKey, which will be used to mint the signed JWT
   * for post login endpoints that will be protected by this token.
   *
   * @return The SecretKey instance.
   */
  @Bean
  public SecretKey secretKey() {
    return Jwts.SIG.HS256.key().build();
  }
}
