package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;

public interface PhotoService {
    byte[] getPhoto(Authentication authentication, Long id);
}
