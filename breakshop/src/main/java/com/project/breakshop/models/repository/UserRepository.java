package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);
    List<User> findByEmail(String email);
    Optional<User> getByEmail(String email);
    Optional<User> getByName(String name);
    boolean existsByEmail(String email);

}
