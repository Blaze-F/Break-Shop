package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Menu> findByStoreId(Long id);

}
