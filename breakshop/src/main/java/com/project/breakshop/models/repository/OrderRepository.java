package com.project.breakshop.models.repository;

import com.project.breakshop.models.DTO.OrderReceiptDTO;
import com.project.breakshop.models.entity.Order;
import com.project.breakshop.models.entity.Order.OrderStatus;
import com.sun.istack.NotNull;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStoreId (Long storeId);

    @Query(value = "UPDATE `ORDER` SET total_price = #{totalPrice}, order_status = #{orderStatus} WHERE id = #{orderId}")
    void completeOrder(@Param("total_price") long total_price, @Param("order_status") String order_status, @Param("order_id") long order_id);

    @Query(value = "        select\n" +
            "            ord.id as orderId,\n" +
            "            ord.order_status as orderStatus,\n" +
            "            ord.user_id as userId,\n" +
            "            user.name as userName,\n" +
            "            user.phone as userPhone,\n" +
            "            user.address as userAddress,\n" +
            "            ord.total_price as totalPrice,\n" +
            "            ord.store_id as storeId,\n" +
            "            store.name as storeName,\n" +
            "            store.address as storeAddress,\n" +
            "            store.phone as storePhone,\n" +
            "            m.id as menuId,\n" +
            "            m.name as menuName,\n" +
            "            m.price as menuPrice,\n" +
            "            ord_m.count as menuCount,\n" +
            "            ord_m_o.option_id as optionId,\n" +
            "            m_o.name as optionName,\n" +
            "            m_o.price as optionPrice\n" +
            "\n" +
            "        from `order` ord\n" +
            "            join order_menu ord_m on ord.id = ord_m.order_id\n" +
            "            join menu m on ord_m.menu_id = m.id\n" +
            "            join order_menu_option ord_m_o on ord_m.order_id = ord_m_o.order_id\n" +
            "            join menu_option m_o on m_o.id = ord_m_o.option_id\n" +
            "            join store on ord.store_id = store.id\n" +
            "            join user on ord.user_id=user.id\n" +
            "        where ord.id = #{orderId};")
    OrderReceiptDTO selectOrderReceipt(long orderId);

    @Modifying
    @Transactional
    @Query("UPDATE `ORDER` SET order_status = :orderStatus, rider_id = :riderId WHERE id = :orderId")
    int updateStandbyOrderToDelivering(@Param("orderId") Long orderId,
                                       @Param("riderId") @NotNull String riderId,
                                       @Param("orderStatus") OrderStatus orderStatus);

    @Modifying
    @Transactional
    @Query("UPDATE Order SET orderStatus = :orderStatus WHERE id = :orderId")
    int finishDeliveringOrder(@Param("orderStatus") OrderStatus orderStatus, @Param("orderId") long orderId);





}
