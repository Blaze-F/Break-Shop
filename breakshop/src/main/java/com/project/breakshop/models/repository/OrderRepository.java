package com.project.breakshop.models.repository;

import com.project.breakshop.models.DTO.OrderReceiptDTO;
import com.project.breakshop.models.entity.Order;
import com.project.breakshop.models.entity.Order.OrderStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStoreId(Long storeId);

    @Modifying
    @Transactional
    @Query("UPDATE Order SET totalPrice = :totalPrice, orderStatus = :orderStatus WHERE id = :orderId")
    void completeOrder(@Param("totalPrice") long totalPrice, @Param("orderStatus") OrderStatus orderStatus, @Param("orderId") long orderId);

    @Query(value = "SELECT new com.example.dto.OrderReceiptDTO(ord.id as orderId, ord.orderStatus as orderStatus, ord.user.id as userId,"
            + "ord.user.name as userName, ord.user.phone as userPhone, ord.user.address as userAddress, ord.totalPrice as totalPrice, "
            + "ord.store.id as storeId, ord.store.name as storeName, ord.store.address as storeAddress, ord.store.phone as storePhone, "
            + "m.id as menuId, m.name as menuName, m.price as menuPrice, ord_m.count as menuCount, ord_m_o.option.id as optionId, "
            + "m_o.name as optionName, m_o.price as optionPrice) "
            + "FROM Order ord JOIN ord.orderMenus ord_m JOIN ord_m.menu m "
            + "JOIN ord_m.orderMenuOptions ord_m_o JOIN ord_m_o.option m_o "
            + "JOIN ord.store JOIN ord.user "
            + "WHERE ord.id = :orderId", nativeQuery = true)
    OrderReceiptDTO selectOrderReceipt(@Param("orderId") long orderId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Order SET orderStatus = :orderStatus, rider.id = :riderId WHERE id = :orderId", nativeQuery = true)
    int updateStandbyOrderToDelivering(@Param("orderId") Long orderId, @Param("riderId") String riderId, @Param("orderStatus") OrderStatus orderStatus);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Order SET orderStatus = :orderStatus WHERE id = :orderId", nativeQuery = true)
    int finishDeliveringOrder(@Param("orderStatus") OrderStatus orderStatus, @Param("orderId") long orderId);

    @Modifying
    @Transactional
    @Query("UPDATE Order SET totalPrice = :totalPrice, orderStatus = :orderStatus WHERE id = :orderId")
    void updateOrder(@Param("totalPrice") String totalPrice, @Param("orderStatus") OrderStatus orderStatus, @Param("orderId") Long orderId);

    @Modifying
    @Transactional
    @Query("UPDATE Order SET orderStatus = :orderStatus WHERE id = :orderId")
    void approveOrder(@Param("orderStatus") OrderStatus orderStatus, @Param("orderId") Long orderId);
}
