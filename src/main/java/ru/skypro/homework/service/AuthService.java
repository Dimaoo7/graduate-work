package ru.skypro.homework.service;

import ru.skypro.homework.dto.Register;

public interface AuthService {
    /**
     * Метод для авторизации
     * @param userName логин в entity это email
     * @param password пароль
     * @return true при успешной авторизации, иначе false
     */
    boolean login(String userName, String password);

    /**
     * Метод для регистрации
     * @param register  регистрация
     * @return true при успешной регистрации, иначе false
     */
    boolean register(Register register);
}
