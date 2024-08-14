package com.project.breakshop.service;

import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.models.DAO.CartItemDAO;
import com.project.breakshop.models.DTO.*;
import com.project.breakshop.models.entity.Order;
import com.project.breakshop.models.entity.Store;
import com.project.breakshop.models.entity.User;
import com.project.breakshop.models.repository.*;
import com.project.breakshop.service.interfaces.PayService;
import com.project.breakshop.utils.PasswordEncrypter;
import com.project.breakshop.utils.PayServiceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@Transactional
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderMenuRepository orderMenuRepository;
    @Mock
    OrderMenuOptionRepository orderMenuOptionRepository;
    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    StoreRepository storeRepository;

    @Mock
    OrderTransactionService orderTransactionService;

    @Mock
    CartItemDAO cartItemDAO;

    @Mock
    PayService payService;

    @Mock
    PayServiceFactory payServiceFactory;
    @Spy
    ModelMapper modelMapper;


    UserDTO user;
    User userEntity;
    StoreDTO store;
    Store storeEntity;
    OrderReceiptDTO orderReceiptDTO;
    Order orderEntity;


    @BeforeEach
    public void setup() {
        user = UserDTO.builder()
                .id(1L)
                .password(PasswordEncrypter.encrypt("123"))
                .email("tjdrnr05571@naver.com")
                .name("장수기")
                .phone("010-1234-1234")
                .address("서울시")
                .level(LoginCheck.UserLevel.ROLE_USER)
                .build();

        userEntity = User.builder()
                .id(1L)
                .password(PasswordEncrypter.encrypt("123"))
                .email("tjdrnr05571@naver.com")
                .name("장수기")
                .phone("010-1234-1234")
                .address("서울시")
                .level(LoginCheck.UserLevel.ROLE_USER)
                .build();

        storeEntity = Store.builder()
                .id(1L)
                .name("아무치킨")
                .address("경기도 성남시 판교로")
                .introduction("엄청 맛있습니다.")
                .build();

        store = StoreDTO.builder()
                .name("60계 치킨")
                .phone("031-921-0101")
                .address("경기도 고양시 일산동")
                .introduction("고추 치킨이 맛있습니다")
                .categoryId(1L)
                .build();

        StoreInfoDTO storeInfoDTO = StoreInfoDTO.builder()
                .storeId(1L)
                .address("경기도 고양시 일산동")
                .phone("031-921-0101")
                .name("60계 치킨")
                .build();

        orderReceiptDTO = OrderReceiptDTO.builder()
                .orderId(1L)
                .orderStatus("COMPLETE_ORDER")
                .totalPrice(2000L)
                .storeInfo(storeInfoDTO)
                .build();

        orderEntity = Order.builder()
                .id(1L)
                .address("anything")
                .orderStatus(Order.OrderStatus.BEFORE_ORDER)
                .totalPrice(0L)
                .build();


    }
    @Before
    public void init(){
        TransactionSynchronizationManager.initSynchronization();
    }
    @After
    public void clear(){
        TransactionSynchronizationManager.clear();
    }

    @Test
    @DisplayName("주문이 정상 완료되는지 확인한다")
    public void testRegisterOrder_Successful() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(cartItemDAO.getCartAndDelete("1")).thenReturn(Collections.emptyList());
        doNothing().when(orderTransactionService).pay(any(PayDTO.PayType.class), anyLong(), anyLong());
        when(orderTransactionService.order(any(OrderDTO.class),anyList(),anyList(),anyList())).thenReturn(2000L);
        when(orderRepository.save(any(Order.class))).thenReturn(orderEntity);
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(storeEntity));

        // When
        OrderReceiptDTO receiptDTO = orderService.registerOrder("1", 1L, PayDTO.PayType.CARD);

        // Then
        assertNotNull(receiptDTO);
        assertEquals("COMPLETE_ORDER", receiptDTO.getOrderStatus());
        verify(orderRepository,atLeastOnce()).save(any(Order.class));
    }

    @Test
    @DisplayName("주문 등록이 취소시 롤백되는지 확인한다")
    public void testRegisterOrder_Rollback() {
        // 런타임 예외 발생
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(storeEntity));
        when(cartItemDAO.getCartAndDelete("1")).thenReturn(Collections.emptyList());

        // When
        assertThrows(RuntimeException.class, () -> orderService.registerOrder("1", 1L, PayDTO.PayType.CARD));

        // then cartItemDAO.insertMenuList
        verify(cartItemDAO, times(1)).insertMenuList(eq("1"), anyList());
    }

    @Test
    @DisplayName("주문 정보 조회가 되는지 확인한다")
    public void testGetOrderInfoByOrderId() {
        // Given
        Order order = Order.builder()
                .id(1L)
                .user(userEntity)
                .store(storeEntity)
                .orderStatus(Order.OrderStatus.COMPLETE_ORDER)
                .totalPrice(2000L)
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // When
        OrderReceiptDTO orderReceiptDTO = orderService.getOrderInfoByOrderId(1L);

        // Then
        assertNotNull(orderReceiptDTO);
        assertEquals(1L, orderReceiptDTO.getOrderId());
        assertEquals("COMPLETE_ORDER", orderReceiptDTO.getOrderStatus());
    }

    @Test
    @DisplayName("주문 정보에서 상점 조회를 할 수 있는지 확인한다")
    public void testGetStoreOrderInfoByStoreId() {
        // Given
        Order order = Order.builder()
                .id(1L)
                .user(userEntity)
                .store(storeEntity)
                .orderStatus(Order.OrderStatus.COMPLETE_ORDER)
                .totalPrice(2000L)
                .build();

        when(orderRepository.findByStoreId(1L)).thenReturn(Collections.singletonList(order));

        // When
        List<OrderStoreDetailDTO> orderStoreDetailDTOList = orderService.getStoreOrderInfoByStoreId(1L);

        // Then
        assertNotNull(orderStoreDetailDTOList);
        assertEquals(1, orderStoreDetailDTOList.size());
    }
}
