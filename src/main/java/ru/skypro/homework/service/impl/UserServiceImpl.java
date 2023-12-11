package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.PasswordIsNotMatchException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.PhotoRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final UserMapper userMapper;
    private final ImageServiceImpl imageService;
    private final PasswordEncoder encoder;
    @Value("${path.to.photos.folder}")
    private String photoPath;

    public UserServiceImpl(UserRepository userRepository, PhotoRepository photoRepository, UserMapper userMapper, ImageServiceImpl imageService, PasswordEncoder encoder1) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.userMapper = userMapper;
        this.imageService = imageService;
        this.encoder = encoder1;
    }


    @Override
    public void setPassword(NewPassword newPass, Authentication authentication) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        String oldPassword = newPass.getCurrentPassword();
        String encodeNewPassword = encoder.encode(newPass.getNewPassword());
        UserEntity userEntity = userRepository.findUserEntityByUserName(authentication.getName());
        if (!encoder.matches(oldPassword, userEntity.getPassword())) {
            throw new PasswordIsNotMatchException("Пароли не совпадают");
        } else {
            userEntity.setPassword(encodeNewPassword);
        }
        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public UserEntity getUser(String username) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        UserEntity user = userRepository.findUserEntityByUserName(username);
        if (user == null) {
            throw new UserNotFoundException("Пользователя с таким логином в базе данных нет");
        }
        return user;
    }

    @Transactional
    @Override
    public UserEntity updateUser(UpdateUser updateUser, Authentication authentication) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        String userName = authentication.getName();
        UserEntity user = userRepository.findUserEntityByUserName(userName);
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(updateUser.getPhone());
        userRepository.save(user);
        return user;
    }
}
