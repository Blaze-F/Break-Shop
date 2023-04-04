package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {
    List<MenuOption> findByMenuId(Long menuId);
}
