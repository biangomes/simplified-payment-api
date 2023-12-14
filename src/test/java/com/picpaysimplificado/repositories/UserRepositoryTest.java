package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

  @Autowired
  UserRepository repo;

  @Autowired
  EntityManager entityManager;

  @Test
  @DisplayName("Should get User successfully from database")
  void findUserByDocumentSuccess() {
    String document = "999.999.999-22";
    UserDTO userDto = new UserDTO("Beatriz", "Gomes", document,
                                  new BigDecimal(20000.00), "biazinha.bjj@gmail.com",
                                  "310319", UserType.COMMON);
    this.createUser(userDto);

    Optional<User> result = this.repo.findUserByDocument(document);
    assertThat(result.isPresent()).isTrue();
  }

  @Test
  @DisplayName("Should not get User from database when user is not found")
  void findUserByDocumentError() {
    String document = "999.999.999-22";
    Optional<User> result = this.repo.findUserByDocument(document);
    assertThat(result.isEmpty()).isTrue();
  }

  private User createUser(UserDTO userDto) {
    User user = new User(userDto);
    this.entityManager.persist(user);
    return user;
  }
}
