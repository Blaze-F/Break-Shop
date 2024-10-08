package com.project.breakshop.service;


import com.project.breakshop.models.DAO.DeliveryDAO;
import com.project.breakshop.models.DTO.PushMessageDTO;
import com.project.breakshop.models.DTO.RiderDTO;
import com.project.breakshop.models.entity.Order;
import com.project.breakshop.models.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class RiderService {

    @Autowired
    public RiderService(DeliveryDAO deliveryDAO, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.deliveryDAO = deliveryDAO;
        //this.pushService = pushService;
    }
    private final DeliveryDAO deliveryDAO;
    private final OrderRepository orderRepository;
    //private final PushService pushService;

    public void registerStandbyRiderWhenStartWork(RiderDTO rider) {
        deliveryDAO.insertStandbyRiderWhenStartWork(rider);
    }

    public void deleteStandbyRiderWhenStopWork(RiderDTO rider) {
        deliveryDAO.deleteStandbyRiderWhenStopWork(rider);
    }

    @Transactional
    public void acceptStandbyOrder(long orderId, RiderDTO rider) {
        deliveryDAO.updateStandbyOrderToDelivering(orderId, rider);
        orderRepository.updateStandbyOrderToDelivering(orderId, rider.getId(), Order.OrderStatus.DELIVERING);
    }

    @Transactional
    public void finishDeliveringOrder(long orderId, RiderDTO rider) {
        orderRepository.finishDeliveringOrder(Order.OrderStatus.COMPLETE_DELIVERY, orderId);
        deliveryDAO.insertStandbyRiderWhenStartWork(rider);
    }


    public void sendMessageToStandbyRidersInSameArea(String address, PushMessageDTO pushMessage) {
        Set<String> tokenSet = deliveryDAO.selectStandbyRiderTokenList(address);

    }

}
