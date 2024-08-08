package com.project.breakshop.service;


import com.project.breakshop.annotation.LoginCheck.UserLevel;
import com.project.breakshop.models.DTO.UserDTO;
import com.project.breakshop.models.DTO.requests.SignupRequest;
import com.project.breakshop.models.entity.User;
import com.project.breakshop.models.repository.UserRepository;
import com.project.breakshop.utils.PasswordEncrypter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Spy
    ModelMapper modelMapper = new ModelMapper();
    UserDTO user;

    User userEntity;

    SignupRequest request;

    Optional<User> userOptional;

    @BeforeEach
    public void makeUser() {
        request = SignupRequest.builder()
                .password(PasswordEncrypter.encrypt("123"))
                .email("tjdrnr05571@naver.com")
                .name("이성국")
                .phone("010-1234-1234")
                .address("서울시")
                .build();
        user = UserDTO.builder()
                .id(1L)
                .password(PasswordEncrypter.encrypt("123"))
                .email("tjdrnr05571@naver.com")
                .name("이성국")
                .phone("010-1234-1234")
                .address("서울시")
                .level(UserLevel.ROLE_USER)
                .build();
        userEntity = User.builder().id(1L)
                .password(PasswordEncrypter.encrypt("123"))
                .email("tjdrnr05571@naver.com")
                .name("이성국")
                .phone("010-1234-1234")
                .address("서울시")
                .level(UserLevel.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("회원가입에 성공합니다")
    public void signUpTestWhenSuccess() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        userService.signUp(request);

        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입에 실패합니다 : 중복된 이메일")
    public void signUpTestWhenFail() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.signUp(request));

        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    @DisplayName("중복된 이메일일 경우 참을 Return합니다")
    public void existsByIdTestWhenReturnTrue() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertTrue(userService.isExistsEmail(user.getEmail()));

        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    @DisplayName("중복된 이메일일 경우 거짓을 Return합니다")
    public void existsByIdTestWhenReturnFalse() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        assertFalse(userService.isExistsEmail(user.getEmail()));

        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    @DisplayName("유저 삭제합니다")
    public void deleteUserTestWhenSuccess() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        userService.deleteUser(user.getEmail());

        verify(userRepository).deleteByEmail(anyString());
    }

    @Test
    @DisplayName("유저 삭제에 실패합니다 : 삭제할 아이디 존재하지 않음")
    public void deleteUserTestWhenFail() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(user.getEmail()));

        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    @DisplayName("유저 비밀번호를 변경합니다")
    public void changeUserPasswordTestWhenSuccess() {
        doNothing().when(userRepository).updateUserPassword(any(String.class), any(String.class));

        userService.changeUserPassword(user.getEmail(), "123");

        verify(userRepository).updateUserPassword(any(String.class), any(String.class));
    }

    @Test
    @DisplayName("아이디와 비밀번호로 유저를 찾습니다")
    public void findUserByIdAndPasswordTestWhenSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        Optional<UserDTO> actualUserDTO = userService.findUserByEmailAndPassword(userEntity.getEmail(), "123");

        assertTrue(actualUserDTO.isPresent());
        assertTrue(actualUserDTO.get() instanceof UserDTO);

        verify(userRepository).findByEmail(userEntity.getEmail());
    }

    @Test
    @DisplayName("아이디와 비밀번호로 유저 찾기에 실패합니다 : 주어진 유저 아이디 존재하지 않음")
    public void findUserByIdAndPasswordTestWhenFailBecauseNotExistId() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertEquals(userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword()),
                Optional.empty());

        verify(userRepository).findByEmail(any(String.class));
    }

    @Test
    @DisplayName("아이디와 비밀번호로 유저 찾기에 실패합니다 : 주어진 유저 비밀번호 오류")
    public void findUserByIdAndPasswordTestWhenFailBecausePasswordError() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        assertEquals(userService.findUserByEmailAndPassword(user.getEmail(), "not same password"),
                Optional.empty());

        verify(userRepository).findByEmail(any(String.class));
    }

}