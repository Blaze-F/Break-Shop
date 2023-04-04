package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.joinTable.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {

}
