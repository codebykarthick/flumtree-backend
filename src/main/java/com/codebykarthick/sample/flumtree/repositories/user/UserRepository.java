package com.codebykarthick.sample.flumtree.repositories.user;

import com.codebykarthick.sample.flumtree.models.entities.User;
import java.util.Optional;
import org.springframework.data.repository.Repository;

/** Spring JPA repository for the USERS table. */
public interface UserRepository extends Repository<User, String> {
  User save(User user);

  Optional<User> findById(String id);

  Optional<User> findByEmail(String email);
}
