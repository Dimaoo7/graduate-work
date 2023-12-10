package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.ModelEntity;
import ru.skypro.homework.entity.PhotoEntity;

import java.io.IOException;
import java.nio.file.Path;

public interface ImageService {

        ModelEntity updateEntitiesPhoto(MultipartFile image, ModelEntity entity) throws IOException;
        boolean saveFileOnDisk(MultipartFile image, Path filePath) throws IOException;
        byte[] getPhotoFromDisk(PhotoEntity photo) throws IOException;
        String getExtension(String fileName);
}
