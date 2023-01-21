package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
