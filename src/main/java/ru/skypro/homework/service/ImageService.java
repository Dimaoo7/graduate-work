package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.ModelEntity;
import ru.skypro.homework.entity.PhotoEntity;

import java.io.IOException;
import java.nio.file.Path;

public interface ImageService {
        /**
         * Обновляем фотографию объектов
         * @param image - полученный MultipartFile
         * @param entity - полученный ModelEntity
         * @throws IOException если фотография объектов не найдена
         * @return ModelEntity модель объекта
         */
        ModelEntity updateEntitiesPhoto(MultipartFile image, ModelEntity entity) throws IOException;
        /**
         * Сохраняем фаил на диск
         * @param image - полученный MultipartFile
         * @param filePath - полученный Path
         * @throws IOException если фаил на диск не сохранился
         */
        boolean saveFileOnDisk(MultipartFile image, Path filePath) throws IOException;
        /**
         * Получаем фотографию с диска
         * @param photo - фотография
         * @throws IOException если фото на диске не найдено
         */
        byte[] getPhotoFromDisk(PhotoEntity photo) throws IOException;
        /**
         * Получаем расширение файла
         * @param fileName - название файла
         */
        String getExtension(String fileName);
}
