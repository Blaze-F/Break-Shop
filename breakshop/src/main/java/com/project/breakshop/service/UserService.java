package com.project.breakshop.service;

import com.project.breakshop.exception.DuplicatedIdException;
import com.project.breakshop.exception.NotExistIdException;
import com.project.breakshop.models.DTO.UserDTO;
import com.project.breakshop.models.entity.User;
import com.project.breakshop.models.repository.UserRepository;
import com.project.breakshop.utils.PasswordEncrypter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Optional;

/*
    @Service 어노테이션은 비즈니스 로직을 처리하는 서비스라는 것을 알려주는 어노테이션이다.
    Component Scan을 통하여 @Service 어노테이션이 붙은 클래스를 스프링이 빈으로 등록하고 이 빈의 라이프사이클을 관리한다.
*/

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public void signUp(UserDTO user) {
        if (isExistsEmail(user.getEmail())) {
            throw new DuplicatedIdException("Same id exists id: " + user.getId());
        }
        UserDTO encryptedUser = encryptUser(user);
        userRepository.save(modelMapper.map(user, User.class));
    }

    public boolean isExistsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDTO findUserById(String id) {
        return modelMapper.map(userRepository.findById(Long.parseLong(id)), UserDTO.class);
    }

    public UserDTO encryptUser(UserDTO user) {
        String encryptedPassword = PasswordEncrypter.encrypt(user.getPassword());
        return UserDTO.builder()
            .id(user.getId())
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

    public void changeUserPassword(String id, String newPassword) {
        //TODO
        userRepository.updateUserPassword(Long.parseLong(id), PasswordEncrypter.encrypt(newPassword));
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



        return modelMapper.map(user.get(), (Type) UserDTO.class);
    }

}

