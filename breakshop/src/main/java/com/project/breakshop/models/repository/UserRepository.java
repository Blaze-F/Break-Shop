package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findUser(); //나중에 레퍼런스 찾기.
}
