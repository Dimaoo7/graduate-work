package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.UserEntity;

public interface UserService {

    void setPassword(NewPassword newPass,Authentication authentication);

    UserEntity getUser(String userName);

    UserEntity updateUser(UpdateUser updateUser,Authentication authentication);





}
