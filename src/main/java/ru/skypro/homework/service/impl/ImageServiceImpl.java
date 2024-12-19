package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.ModelEntity;
import ru.skypro.homework.entity.PhotoEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.PhotoRepository;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final PhotoRepository photoRepository;
    private final UserMapper userMapper;
    @Value("${path.to.photos.folder}")
    private String photoDir;

    public ImageServiceImpl(PhotoRepository photoRepository, UserMapper userMapper) {
        this.photoRepository = photoRepository;
        this.userMapper = userMapper;
    }


    @Override
    public ModelEntity updateEntitiesPhoto(MultipartFile image, ModelEntity entity) throws IOException {

        if (entity.getPhoto() != null) {
            photoRepository.delete(entity.getPhoto());
        }

        PhotoEntity photoEntity = userMapper.mapMultipartFileToPhoto(image);
        log.info("Создана сущность photoEntity - {}", photoEntity != null);
        entity.setPhoto(photoEntity);
        photoRepository.save(photoEntity);

        String urlToAvatar = "/photo/image/" + entity.getPhoto().getId();
        log.info("URL для перехода фронта к методу возврата аватара: {}", urlToAvatar);

        entity.setImage(urlToAvatar);


        Path filePath = Path.of(photoDir, entity.getPhoto().getId() + "."
                + this.getExtension(image.getOriginalFilename()));
        log.info("путь к файлу для хранения фото на ПК: {}", filePath);

        entity.getPhoto().setFilePath(filePath.toString());

        entity.setFilePath(filePath.toString());

        this.saveFileOnDisk(image, filePath);

        return entity;
    }

    @Override
    public boolean saveFileOnDisk(MultipartFile image, Path filePath) throws IOException {

        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = image.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return true;
    }
    public PhotoEntity saveFileOnDisk(PhotoEntity photo, Path filePath) throws IOException {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = new ByteArrayInputStream(photo.getData());
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return photo;
    }

    public byte[] getPhotoFromDisk(PhotoEntity photo) {

        Path path1 = Path.of(photo.getFilePath());
        try {
            return Files.readAllBytes(path1);
        } catch (IOException e) {
            throw new NoSuchFieldException("Искомый файл аватара или фото объявления, отсутствует на ПК\n" +
                    "Поиск файла перенаправлен в БД");
        } finally {
            return null;
        }
    }


    @Override
    public String getExtension(String fileName) {

        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
