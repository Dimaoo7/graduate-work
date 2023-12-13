package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.PhotoEntity;
import ru.skypro.homework.entity.UserEntity;

import java.io.IOException;
import java.nio.file.Path;

public interface ImageService {

        void updateUserImage(UserEntity user, MultipartFile image, Path filePath);
        PhotoEntity updateAdImage(AdEntity ad, MultipartFile image, Path filePath) throws IOException;
        boolean saveFileOnDisk(MultipartFile image, Path filePath) throws IOException;
        String getExtension(String fileName);

}
