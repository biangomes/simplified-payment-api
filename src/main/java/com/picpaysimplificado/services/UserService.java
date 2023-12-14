package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository repo;

  public void validateTransaction(User sender, BigDecimal amount) throws Exception {
    if (sender.getUserType() == UserType.MERCHANT) {
      throw new Exception("Usuario do tipo Lojista nao esta autorizado a realizar a transacao");
    }

    if (sender.getBalance().compareTo(amount) < 0) {
      throw new Exception("Saldo insuficiente");
    }
  }

  public User findUserById(Long id) throws Exception {
    return this.repo.findUserById(id).orElseThrow(() -> new Exception("Usuario nao encontrado"));
  }

  public User createUser(UserDTO userDto) {
    User newUser = new User(userDto);
    this.saveUser(newUser);
    return newUser;
  }

  public List<User> getAllUsers() {
    return this.repo.findAll();
  }

  public void saveUser(User user) {
    this.repo.save(user);
  }
}
