package com.project.breakshop.models.repository;



import com.project.breakshop.models.entity.joinTable.OrderMenuOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuOptionRepository extends JpaRepository<OrderMenuOption, Long> {


}
