package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
}
