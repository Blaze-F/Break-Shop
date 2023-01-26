package com.project.breakshop.service;

import com.flab.makedel.dao.DeliveryDAO;
import com.flab.makedel.dto.OrderReceiptDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryDAO deliveryDAO;

    public void registerStandbyOrderWhenOrderApprove(long orderId, OrderReceiptDTO orderReceipt) {
        deliveryDAO.insertStandbyOrderWhenOrderApprove(orderId, orderReceipt);
    }

    public OrderReceiptDTO loadStandbyOrder(long orderId, String riderAddress) {
        return deliveryDAO.selectStandbyOrder(orderId, riderAddress);
    }

    public List<String> loadStandbyOrderList(String riderAddress) {
        return deliveryDAO.selectStandbyOrderList(riderAddress);
    }

}
