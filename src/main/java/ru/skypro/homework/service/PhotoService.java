package ru.skypro.homework.service;

import java.io.IOException;

public interface PhotoService {
    /**
     * Получение фото
     * @param id ID
     * @throws IOException если фото не получено
     */
    byte[] getPhoto(Long id) throws IOException;
}
