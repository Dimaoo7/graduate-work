package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;

@Service
@Slf4j
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        UserEntity userEntity = userRepository.findUserEntityByUserName(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return User.builder()
                .username(userEntity.getUserName())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().name())
                .build();
    }
}