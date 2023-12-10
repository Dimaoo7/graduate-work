package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.PhotoEntity;
import ru.skypro.homework.exception.PhotoOnDatabaseIsAbsentException;
import ru.skypro.homework.exception.PhotoOnPcIsAbsentException;
import ru.skypro.homework.repository.PhotoRepository;
import ru.skypro.homework.service.PhotoService;

@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    private final ImageServiceImpl imageService;

    public PhotoServiceImpl(PhotoRepository photoRepository, ImageServiceImpl imageService) {
        this.photoRepository = photoRepository;
        this.imageService = imageService;
    }

    public byte[] getPhoto(Long photoId) {
        log.info("Method {}", LoggingMethodImpl.getMethodName());
        log.info("photoId: {}", photoId);

        PhotoEntity photo = photoRepository.findById(photoId).orElseThrow(PhotoOnDatabaseIsAbsentException::new);
        log.info("Фото найдено - {}", photo.getData() != null);

        if (imageService.getPhotoFromDisk(photo) == null) {
            return photoRepository.findById(photoId).orElseThrow(PhotoOnPcIsAbsentException::new).getData();
        }

        return imageService.getPhotoFromDisk(photo);
    }
}
