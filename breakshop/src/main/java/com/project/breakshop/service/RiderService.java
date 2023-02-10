package com.project.breakshop.service;


import com.google.firebase.messaging.Message;
import com.project.breakshop.Redis.DeliveryDAO;
import com.project.breakshop.models.DTO.OrderDTO.OrderStatus;
import com.project.breakshop.models.DTO.PushMessageDTO;
import com.project.breakshop.models.DTO.RiderDTO;
import com.project.breakshop.models.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final DeliveryDAO deliveryDAO;
    private final OrderRepository orderRepository;
    private final PushService pushService;

    public void registerStandbyRiderWhenStartWork(RiderDTO rider) {
        deliveryDAO.insertStandbyRiderWhenStartWork(rider);
    }

    public void deleteStandbyRiderWhenStopWork(RiderDTO rider) {
        deliveryDAO.deleteStandbyRiderWhenStopWork(rider);
    }

    //TODO
    @Transactional
    public void acceptStandbyOrder(long orderId, RiderDTO rider) {
        deliveryDAO.updateStandbyOrderToDelivering(orderId, rider);
        orderMapper.updateStandbyOrderToDelivering(orderId, rider.getId(), OrderStatus.DELIVERING);
    }

    @Transactional
    public void finishDeliveringOrder(long orderId, RiderDTO rider) {
        orderMapper.finishDeliveringOrder(orderId, OrderStatus.COMPLETE_DELIVERY);
        deliveryDAO.insertStandbyRiderWhenStartWork(rider);
    }

    public void sendMessageToStandbyRidersInSameArea(String address, PushMessageDTO pushMessage) {
        Set<String> tokenSet = deliveryDAO.selectStandbyRiderTokenList(address);
        List<Message> messages = tokenSet.stream().map(token -> Message.builder()
            .putData("title", pushMessage.getTitle())
            .putData("content", pushMessage.getContent())
            .putData("orderReceipt", pushMessage.getOrderReceipt().toString())
            .putData("createdAt", pushMessage.getCreatedAt())
            .setToken(token)
            .build())
            .collect(Collectors.toList());

        pushService.sendMessages(messages);
    }

}
