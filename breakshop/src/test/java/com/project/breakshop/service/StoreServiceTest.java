package com.project.breakshop.service;


import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.models.DTO.*;
import com.project.breakshop.models.DTO.requests.CreateStoreRequest;
import com.project.breakshop.models.entity.Order.OrderStatus;
import com.project.breakshop.models.entity.Order;
import com.project.breakshop.models.entity.Store;
import com.project.breakshop.models.entity.StoreCategory;
import com.project.breakshop.models.entity.User;
import com.project.breakshop.models.repository.OrderRepository;
import com.project.breakshop.models.repository.StoreCategoryRepository;
import com.project.breakshop.models.repository.StoreRepository;
import com.project.breakshop.models.repository.UserRepository;
import com.project.breakshop.utils.PasswordEncrypter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {
    @Spy
    ModelMapper modelMapper;
    @Mock
    StoreRepository storeRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    StoreCategoryRepository storeCategoryRepository;
    @Mock
    DeliveryService deliveryService;

    @Mock
    RiderService riderService;

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;
    @InjectMocks
    StoreService storeService;

    User userEntity;
    UserDTO user;

    StoreDTO store;
    Store storeEntity;
    CreateStoreRequest createStoreRequest;
    StoreCategory storeCategory;
    StoreCategoryDTO storeCategoryDTO;

    Set<Store> storeSet = new HashSet<>();

    @BeforeEach
    public void makeStore() {
        user = UserDTO.builder()
                .id(1L)
                .password(PasswordEncrypter.encrypt("123"))
                .email("tjdrnr05571@naver.com")
                .name("장수기")
                .phone("010-1234-1234")
                .address("서울시")
                .level(LoginCheck.UserLevel.ROLE_USER)
                .build();

        userEntity = User.builder().id(1L)
                .password(PasswordEncrypter.encrypt("123"))
                .email("tjdrnr05571@naver.com")
                .name("장수기")
                .phone("010-1234-1234")
                .address("서울시")
                .level(LoginCheck.UserLevel.ROLE_USER)
                .build();

        createStoreRequest = CreateStoreRequest.builder()
                .address("any")
                .ownerId(1L)
                .categoryName("any")
                .introduction("any")
                .name("any")
                .phone("any")
                .build();

        storeCategory = StoreCategory.builder()
                .name("category")
                .id(1L)
                .storeSet(storeSet).build();
        store = StoreDTO.builder()
            .name("60계 치킨")
            .phone("031-921-0101")
            .address("경기도 고양시 일산동")
            .introduction("고추 치킨이 맛있습니다")
            .categoryId(1L)
            .build();

        storeEntity = Store.builder()
                .id(1L)
                .name("아무치킨")
                .address("경기도 성남시 판교로")
                .introduction("엄청 맛있습니다.")
                .build();

        storeSet.add(storeEntity);
    }

    @Test
    @DisplayName("가게 생성에 성공합니다")
    public void insertStoreTestSuccess() {
        when(storeRepository.save(any(Store.class))).thenReturn(storeEntity);
        when(userRepository.getByEmail(anyString())).thenReturn(Optional.of(userEntity));
        when(storeCategoryRepository.existsByName(anyString())).thenReturn(true);
        when(storeCategoryRepository.getByName(anyString())).thenReturn(Optional.of(storeCategory));

        storeService.insertStore(createStoreRequest, "1L");
        verify(storeRepository).save(any(Store.class));
    }

    @Test
    @DisplayName("가게 생성에 실패합니다 : 중복된 가게 이름")
    public void insertStoreTestFailBecauseSameStoreName() {
        when(userRepository.getByEmail(anyString())).thenReturn(Optional.of(userEntity));
        when(storeCategoryRepository.existsByName(anyString())).thenReturn(true);
        when(storeCategoryRepository.getByName(anyString())).thenReturn(Optional.of(storeCategory));
        doThrow(RuntimeException.class).when(storeRepository).save(any(Store.class));

        assertThrows(RuntimeException.class,
            () -> storeService.insertStore(createStoreRequest, "example@gmail.com"));

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
        when(storeRepository.getByUserEmailAndId(anyString(), anyLong())).thenReturn(Optional.of(storeEntity));

        storeService.getMyStore(43, "N");

        verify(storeRepository).getByUserEmailAndId(anyString(), anyLong());
    }


    @Test
    @DisplayName("자신의 가게가 맞는지 확인하는데 성공합니다.")
    public void validateMyStoreTestSuccess() {
        when(storeRepository.existsByIdAndUserEmail(anyLong(), anyString())).thenReturn(true);
        storeService.validateMyStore(3, "1L");

        verify(storeRepository).existsByIdAndUserEmail(anyLong(), anyString());
    }

    @Test
    @DisplayName("자신의 가게가 맞는지 확인하는데 실패합니다 : 본인 소유의 가게가 아님")
    public void validateMyStoreTestFail() {
        when(storeRepository.existsByIdAndUserEmail(anyLong(), anyString())).thenReturn(false);

        assertThrows(HttpClientErrorException.class,
            () -> storeService.validateMyStore(3, "1L"));

        verify(storeRepository).existsByIdAndUserEmail(anyLong(), anyString());
    }

    @Test
    @DisplayName("사장이 주문을 승인하는데 성공합니다")
    public void approveOrderTestSuccess() {
        UserInfoDTO userInfo = UserInfoDTO.builder()
            .id("사용자1")
            .name("장수기")
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

        Order orderEntity = Order.builder()
                .id(1L)
                .address("address")
                .build();

        doNothing().when(orderRepository).approveOrder(any(Order.OrderStatus.class), anyLong());
        when(orderRepository.selectOrderReceipt(anyLong())).thenReturn(orderReceiptDTO);
        doNothing().when(deliveryService)
            .registerStandbyOrderWhenOrderApprove(anyLong(), any(OrderReceiptDTO.class));
        doNothing().when(riderService)
            .sendMessageToStandbyRidersInSameArea(any(String.class), any(PushMessageDTO.class));
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orderEntity));

        storeService.approveOrder(1);

        verify(orderRepository).approveOrder(any(OrderStatus.class), anyLong());
        verify(orderRepository).selectOrderReceipt(anyLong());
        verify(deliveryService)
            .registerStandbyOrderWhenOrderApprove(anyLong(), any(OrderReceiptDTO.class));
        verify(riderService)
            .sendMessageToStandbyRidersInSameArea(any(String.class), any(PushMessageDTO.class));
    }

}
