package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {
    /**
     * Обновление Avatar
     * @param id ID пользователя
     * @param image полученный MultipartFile
     * @param authentication идентификация пользователя
     */
    void updateAvatar(Long id, MultipartFile image, Authentication authentication);
}
