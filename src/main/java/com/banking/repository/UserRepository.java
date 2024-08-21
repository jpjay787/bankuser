package com.banking.repository;


import com.banking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameContaining(String name);
    List<User> findByEmailContaining(String email);

}
