package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.Store;
import com.project.breakshop.models.entity.StoreCategory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long> {
    List<StoreCategory> findAll();

}
