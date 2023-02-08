package com.project.breakshop.models.repository;

import com.project.breakshop.models.DTO.UserDTO;
import com.project.breakshop.models.DTO.UserInfoDTO;
import com.project.breakshop.models.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);
    List<User> findByEmail(String email);
    Optional<User> getByEmail(String email);
    Optional<User> getByName(String name);
    boolean existsByEmail(String email);

    @Query(value =
            "SELECT" + " id, name, phone, address"+" FROM"+ " USER"+" WHERE "+ "user.id = :id" )
    UserInfoDTO selectUserInfo(@Param("id") Long id);
}
