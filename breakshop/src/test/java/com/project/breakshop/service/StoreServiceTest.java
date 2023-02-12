package com.project.breakshop.service;


import com.project.breakshop.models.DTO.StoreDTO;
import com.project.breakshop.models.entity.Store;
import com.project.breakshop.models.repository.OrderRepository;
import com.project.breakshop.models.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    StoreRepository storeRepository;

    @Mock
    OrderRepository orderRepository;
    @Mock
    DeliveryService deliveryService;

    @Mock
    RiderService riderService;

    @InjectMocks
    StoreService storeService;

    StoreDTO store;

    @BeforeEach
    public void makeStore() {
        store = StoreDTO.builder()
            .name("60계 치킨")
            .phone("031-921-0101")
            .address("경기도 고양시 일산동")
            .introduction("고추 치킨이 맛있습니다")
            .categoryId(1L)
            .build();
    }

    @Test
    @DisplayName("가게 생성에 성공합니다")
    public void insertStoreTestSuccess() {
        doNothing().when(storeRepository).save(any(Store.class));

        storeService.insertStore(store, "60owners");

        verify(storeRepository).save(any(Store.class));
    }

    @Test
    @DisplayName("가게 생성에 실패합니다 : 중복된 가게 이름")
    public void insertStoreTestFailBecauseSameStoreName() {
        doThrow(RuntimeException.class).when(storeRepository).save(any(Store.class));

        assertThrows(RuntimeException.class,
            () -> storeService.insertStore(store, "60owners"));

        verify(storeRepository).save(any(Store.class));
    }

    @Test
    @DisplayName("사장이 내 가게 전체 목록을 조회하는데 성공합니다")
    public void getMyAllStoreTestSuccess() {
        when(storeRepository.findByUserEmail(any(String.class)))
            .thenReturn(anyList());

        storeService.findMyAllStore("owner");

        verify(storeRepository).findByUserEmail(any(String.class));
    }

    @Test
    @DisplayName("가게를 소유하지 않은 사장이 자신의 가게 목록을 조회하면 빈 List를 리턴합니다")
    public void getMyAllStoreTestReturnEmptyArray() {
        List<Store> tempList = new ArrayList<>();
        when(storeRepository.findByUserEmail(any(String.class))).thenReturn(tempList);

        storeService.findMyAllStore("owner");

        verify(storeRepository).findByUserEmail(any(String.class));
    }

    @Test
    @DisplayName("사장이 내 특정 가게 목록을 조회하는데 성공합니다")
    public void getMyStoreTestSuccess() {
        when(storeRepository.getByUserEmailAndId(anyLong(), any(String.class)))
            .thenReturn(store);

        storeService.getMyStore(43, "owner");

        verify(storeMapper).selectStore(anyLong(), any(String.class));
    }


    @Test
    @DisplayName("자신의 가게가 맞는지 확인하는데 성공합니다.")
    public void validateMyStoreTestSuccess() {
        when(storeMapper.isMyStore(anyLong(), any(String.class))).thenReturn(true);

        storeService.validateMyStore(3, "owner");

        verify(storeMapper).isMyStore(anyLong(), any(String.class));
    }

    @Test
    @DisplayName("자신의 가게가 맞는지 확인하는데 실패합니다 : 본인 소유의 가게가 아님")
    public void validateMyStoreTestFail() {
        when(storeMapper.isMyStore(anyLong(), any(String.class))).thenReturn(false);

        assertThrows(HttpClientErrorException.class,
            () -> storeService.validateMyStore(3, "owner1"));

        verify(storeMapper).isMyStore(anyLong(), any(String.class));
    }

    @Test
    @DisplayName("사장이 주문을 승인하는데 성공합니다")
    public void approveOrderTestSuccess() {

        UserInfoDTO userInfo = UserInfoDTO.builder()
            .id("사용자1")
            .name("이성국")
            .phone("010-1212-1212")
            .address("경기도 고양시 일산동")
            .build();

        StoreInfoDTO storeInfo = StoreInfoDTO.builder()
            .storeId(1L)
            .name("bbq치킨")
            .phone("010-1234-1234")
            .address("경기도 고양시 일산동")
            .build();

        List<CartItemDTO> cartList = new ArrayList<>();

        OrderReceiptDTO orderReceiptDTO = OrderReceiptDTO.builder()
            .orderId(1L)
            .orderStatus("COMPLETE_ORDER")
            .userInfo(userInfo)
            .storeInfo(storeInfo)
            .totalPrice(123000L)
            .cartList(cartList)
            .build();

        doNothing().when(orderMapper).approveOrder(anyLong(), any(OrderStatus.class));
        when(orderMapper.selectOrderReceipt(anyLong())).thenReturn(orderReceiptDTO);
        doNothing().when(deliveryService)
            .registerStandbyOrderWhenOrderApprove(anyLong(), any(OrderReceiptDTO.class));
        doNothing().when(riderService)
            .sendMessageToStandbyRidersInSameArea(any(String.class), any(PushMessageDTO.class));

        storeService.approveOrder(1);

        verify(orderMapper).approveOrder(anyLong(), any(OrderStatus.class));
        verify(orderMapper).selectOrderReceipt(anyLong());
        verify(deliveryService)
            .registerStandbyOrderWhenOrderApprove(anyLong(), any(OrderReceiptDTO.class));
        verify(riderService)
            .sendMessageToStandbyRidersInSameArea(any(String.class), any(PushMessageDTO.class));
    }

}
