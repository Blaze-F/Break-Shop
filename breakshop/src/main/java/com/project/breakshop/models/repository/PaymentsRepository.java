package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {

    Optional<Payments> findById(Long id);

    Optional<Payments> findByOrderId(Long id);
}
