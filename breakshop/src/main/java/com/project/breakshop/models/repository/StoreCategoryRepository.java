package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long> {
    List<StoreCategory> findAll();

    StoreCategory getById(Long id);

}
