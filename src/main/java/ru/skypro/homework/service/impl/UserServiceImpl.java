package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthServiceImpl authService;

    public UserServiceImpl(UserRepository userRepository, AuthServiceImpl authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }


    @Override
    public void setPassword(NewPassword newPass) {
        String oldPassword = newPass.getCurrentPassword();
        String newPassword = newPass.getNewPassword();
        UserEntity userEntity = userRepository.findUserEntityByPassword(oldPassword);
        userEntity.setPassword(newPassword);
        userRepository.save(userEntity);

        authService.getUserEntity().setPassword(newPassword);

    }

    @Override
    public UserEntity getUser(Authentication authentication) {
        String userName = authService.getLogin().getUsername();
        return userRepository.findUserEntityByUserName(userName);
    }

    @Override
    public UserEntity updateUser(UpdateUser updateUser) {
        String userName = authService.getLogin().getUsername();
        UserEntity user = userRepository.findUserEntityByUserName(userName);
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(updateUser.getPhone());
        userRepository.save(user);
        return user;
    }

    @Override
    public UserEntity checkUserByUsername(String userName) {
        UserEntity user = userRepository.findUserEntityByUserName(userName);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }
}
