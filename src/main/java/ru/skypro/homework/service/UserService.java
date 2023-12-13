package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.UserEntity;

public interface UserService {
    /**
     * Обновление пароля.
     * @param newPass - новый пароль
     * @param authentication идентификация пользователя
     */
    void setPassword(NewPassword newPass,Authentication authentication);
    /**
     * Получение информации об авторизованном пользователе.
     * @param userName - имя пользователя
     * @return UserEntity - информация о пользователе
     */
    UserEntity getUser(String userName);
    /**
     * Обновление информации об авторизованном пользователе.
     * @param updateUser - обновление пользователя
     * @param authentication идентификация пользователя
     * @return UserEntity - информация о пользователе
     */
    UserEntity updateUser(UpdateUser updateUser,Authentication authentication);





}
