package com.project.breakshop.service;

import com.project.breakshop.exception.DuplicatedIdException;
import com.project.breakshop.exception.NotExistIdException;
import com.project.breakshop.models.DTO.UserDTO;
import com.project.breakshop.models.DTO.UserInfoDTO;
import com.project.breakshop.models.DTO.requests.SignupRequest;
import com.project.breakshop.models.entity.User;
import com.project.breakshop.models.repository.UserRepository;
import com.project.breakshop.utils.PasswordEncrypter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.lang.reflect.Type;
import java.util.Optional;

/*
    @Service 어노테이션은 비즈니스 로직을 처리하는 서비스라는 것을 알려주는 어노테이션이다.
    Component Scan을 통하여 @Service 어노테이션이 붙은 클래스를 스프링이 빈으로 등록하고 이 빈의 라이프사이클을 관리한다.
*/

@Service
public class UserService {
    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }
    private final UserRepository userRepository;
    final ModelMapper modelMapper;

    public void signUp(SignupRequest request) {
        if (isExistsEmail(request.getEmail())) {
            throw new DuplicatedIdException("Same id exists email: " + request.getEmail());
        }
        UserDTO encryptedUser = encryptUser(request);
        userRepository.save(modelMapper.map(encryptedUser, User.class));
    }

    public boolean isExistsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDTO findUserById(String id) {
        return modelMapper.map(userRepository.findById(Long.parseLong(id)), UserDTO.class);
    }

    public UserDTO findUserByEmail(String email){
        return modelMapper.map(userRepository.findByEmail(email), UserDTO.class);
    }

    public UserDTO encryptUser(SignupRequest user) {
        String encryptedPassword = PasswordEncrypter.encrypt(user.getPassword());
        return UserDTO.builder()
            .password(encryptedPassword)
            .email(user.getEmail())
            .name(user.getName())
            .phone(user.getPhone())
            .address(user.getAddress())
            .level(user.getLevel())
            .build();
    }


    public void deleteUser(String email) {
        if (!isExistsEmail(email)) {
            throw new NotExistIdException("Not exists id");
        }
        userRepository.deleteByEmail(email);
    }

    public void changeUserPassword(String email, String newPassword) {

        userRepository.updateUserPassword(email, PasswordEncrypter.encrypt(newPassword));
    }

    public Optional<UserDTO> findUserByEmailAndPassword(String email, String password) {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return Optional.empty();
        }

        boolean isSamePassword = PasswordEncrypter.isMatch(password, user.get().getPassword());

        if (!isSamePassword) {
            return Optional.empty();
        }

        return Optional.of(modelMapper.map(user.get(), UserDTO.class));
    }


}

