package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.AvatarEntity;
import ru.skypro.homework.entity.PhotoEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.PhotoRepository;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final AvatarRepository avatarRepository;
    private final PhotoRepository photoRepository;

    public ImageServiceImpl(AvatarRepository avatarRepository, PhotoRepository photoRepository) {
        this.avatarRepository = avatarRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public void updateUserImage(UserEntity user, MultipartFile image, Path filePath) {
        log.info("Started method {}", LoggingMethodImpl.getMethodName());
        AvatarEntity avatar = avatarRepository.findByUser(user).orElseGet(AvatarEntity::new);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(image.getSize());
        avatar.setMediaType(image.getContentType());
        avatar.setUser(user);
        avatarRepository.save(avatar);
    }

    @Override
    public PhotoEntity updateAdImage(AdEntity ad, MultipartFile image, Path filePath) throws IOException {
        log.info("Started method {}", LoggingMethodImpl.getMethodName());
        PhotoEntity photo = photoRepository.findByAd(ad).orElseGet(PhotoEntity::new);
        photo.setFilePath(filePath.toString());
        photo.setFileSize(image.getSize());
        photo.setMediaType(image.getContentType());
        photo.setData(image.getBytes());
        photo.setAd(ad);
        return photoRepository.save(photo);
    }


    public AvatarEntity mapMultipartFileToAvatar(MultipartFile image) {
        log.info("Started method {}", LoggingMethodImpl.getMethodName());
        AvatarEntity avatar = new AvatarEntity();
        try {
            avatar.setData(image.getBytes());
            avatar.setMediaType(image.getContentType());
            avatar.setFileSize(image.getSize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return avatar;
    }

    @Override
    public String getExtension(String fileName) {
        log.info("Started method {}", LoggingMethodImpl.getMethodName());
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public PhotoEntity saveFileOnDisk(PhotoEntity photo, Path filePath) throws IOException {
        log.info("Started method {}", LoggingMethodImpl.getMethodName());
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

    @Override
    public boolean saveFileOnDisk(MultipartFile image, Path filePath) throws IOException {
        log.info("Started method {}", LoggingMethodImpl.getMethodName());
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
}
