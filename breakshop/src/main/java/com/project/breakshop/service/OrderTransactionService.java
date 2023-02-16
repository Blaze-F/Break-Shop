package com.project.breakshop.service;


import com.project.breakshop.models.DTO.*;
import com.project.breakshop.models.entity.Order;
import com.project.breakshop.models.entity.joinTable.OrderMenu;
import com.project.breakshop.models.entity.joinTable.OrderMenuOption;
import com.project.breakshop.models.repository.OrderMenuOptionRepository;
import com.project.breakshop.models.repository.OrderMenuRepository;
import com.project.breakshop.models.repository.OrderRepository;
import com.project.breakshop.utils.PayServiceFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
    굳이 OrderTransactionService를 새로 만들은 이유 :
    우선 @Transactional은 AOP로 구현되어있어 RTW방식으로 프록시를 생성합니다.
    따라서 같은 서비스 내에 있는 메소드를 호출할때는 Transactional은 새롭게 애노테이션이 적용되지 않습니다.
    지금 현재는 주문과 결제가 같은 트랜잭션에서 처리되야 하므로
    굳이 OrderTransactionService를 만들어서 트랜잭션을 분리할 필요는 없지만
    추후에 주문과 결제가 같은 트랜잭션에서 처리되지 않게 변경하게 될 수도 있으므로
    Transaction 전파 레벨이 변경되어야 할 수도 있다고 판단하여 분리하였습니다.
    전파레벨 설정을 기본값으로 해준 이유는 주문 트랜잭션안에서
    주문,결제가 모두 이루어져 커밋이 같이되어야하고, 롤백또한 같이 되어야하기 때문입니다.
    전파레벨 설정이 모두 기본값인 이유는 자식 트랜잭션이 부모 트랜잭션에 합류하여
    같은 트랜잭션에서 커밋,롤백이 되어야 하는 로직이기 때문입니다.
    주문과 결제가 동시에 이루어져야 합니다.
 */

@Service
@RequiredArgsConstructor
public class OrderTransactionService {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final OrderMenuRepository orderMenuRepository;

    @Autowired
    private final OrderMenuOptionRepository orderMenuOptionRepository;
    @Autowired
    private final PayServiceFactory payServiceFactory;


    private ModelMapper modelMapper;

    @Transactional
    public long order(OrderDTO orderDTO, List<CartItemDTO> cartList,
                      List<OrderMenuDTO> orderMenuList, List<OrderMenuOptionDTO> orderMenuOptionList) {

        orderRepository.save(modelMapper.map(orderDTO, Order.class));

        return registerOrderMenu(cartList, orderDTO.getId(), orderMenuList,
            orderMenuOptionList);
    }

    @Transactional
    public void pay(PayDTO.PayType payType, long totalPrice, long orderId) {

        PayService payService = payServiceFactory.getPayService(payType);
        payService.pay(totalPrice, orderId);

    }

    private long registerOrderMenu(List<CartItemDTO> cartList, Long orderId,
        List<OrderMenuDTO> orderMenuList, List<OrderMenuOptionDTO> orderMenuOptionList) {

        long totalPrice = 0;

        for (int i = 0; i < cartList.size(); i++) {
            CartItemDTO cart = cartList.get(i);
            totalPrice += cart.getPrice() * cart.getCount();

            OrderMenuDTO orderMenuDTO = OrderMenuDTO.builder()
                .orderId(orderId)
                .menuId(cart.getMenuId())
                .count(cart.getCount())
                .build();
            orderMenuList.add(orderMenuDTO);

            for (int j = 0; j < cart.getOptionList().size(); j++) {
                CartOptionDTO option = cart.getOptionList().get(j);
                totalPrice += option.getPrice();

                OrderMenuOptionDTO orderMenuOptionDTO = OrderMenuOptionDTO.builder()
                    .optionId(option.getOptionId())
                    .menuId(cart.getMenuId())
                    .orderId(orderId)
                    .build();
                orderMenuOptionList.add(orderMenuOptionDTO);
            }
        }

        orderMenuRepository.saveAll(orderMenuList.stream().map(e-> modelMapper.map(e, OrderMenu.class)).collect(Collectors.toSet()));
        orderMenuOptionRepository.saveAll(orderMenuOptionList.stream().map(e -> modelMapper.map(e, OrderMenuOption.class)).collect(Collectors.toList()));

        return totalPrice;

    }

}
