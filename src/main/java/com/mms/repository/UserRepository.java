package com.mms.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mms.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}