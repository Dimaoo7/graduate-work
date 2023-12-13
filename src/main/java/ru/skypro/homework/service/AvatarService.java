package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {
    void updateAvatar(Long id, MultipartFile image, Authentication authentication) throws IOException;
}
