package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {
    void updateAvatar(Long id, MultipartFile image) throws IOException;
}
