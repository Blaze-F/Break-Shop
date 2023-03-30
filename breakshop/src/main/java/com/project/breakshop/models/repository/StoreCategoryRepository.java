package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long> {
    List<StoreCategory> findAll();

    StoreCategory getById(Long id);
    Optional<StoreCategory> getByName(String name);
    boolean existsById(Long id);
    boolean existsByName(String name);
}
