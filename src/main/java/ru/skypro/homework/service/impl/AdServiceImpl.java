package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.PhotoEntity;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.PhotoRepository;
import ru.skypro.homework.service.AdService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final PhotoRepository photoRepository;
    private final ImageServiceImpl imageServiceImpl;
    private final AdMapper adMapper;
    private final UserServiceImpl userServiceImpl;
    @Value("${path.to.photos.folder}")
    private final String photoDir;

    public AdServiceImpl(AdRepository adRepository,
                         PhotoRepository photoRepository, ImageServiceImpl imageServiceImpl, AdMapper adMapper,
                         UserServiceImpl userServiceImpl, @Value("${path.to.photos.folder}") String photoDir) {
        this.adRepository = adRepository;
        this.photoRepository = photoRepository;
        this.imageServiceImpl = imageServiceImpl;
        this.adMapper = adMapper;
        this.userServiceImpl = userServiceImpl;
        this.photoDir = photoDir;
    }

    @Override
    public Ads getAllAds() {
        List<Ad> dtos = adRepository.findAll().stream()
                .map(adMapper::mapToAdDto)
                .collect(Collectors.toList());
        return new Ads(dtos.size(), dtos);
    }

    @Override
    public Ad addAd(CreateOrUpdateAd properties, MultipartFile image, Authentication authentication) throws IOException {
        AdEntity adEntity = new AdEntity();
        adEntity.setTitle(properties.getTitle());
        adEntity.setPrice(properties.getPrice());
        adEntity.setDescription(properties.getDescription());

        adEntity.setPhoto(adMapper.mapToMultipartPhoto(image));
        adEntity.setAuthor(userServiceImpl.getUser(authentication));
        adEntity.setImage("photo/image" + adEntity.getPhoto().getId());
        log.info("image = " + adEntity.getImage());

        Path path = Path.of(photoDir,adEntity.getPhoto().getId() +
                "." + imageServiceImpl.getExtension(image.getOriginalFilename()));

        log.info("path = " + path);

        imageServiceImpl.saveFileOnDisk(adEntity.getPhoto(), path);

        adRepository.save(adEntity);

        adEntity = adRepository.findByAuthor(authentication.getName());

        adEntity.getPhoto().setAd(adEntity);

        photoRepository.save(adEntity.getPhoto());

        return adMapper.mapToAdDto(adEntity);

    }

    @Override
    public ExtendedAd getAds(Long id) {
        AdEntity entity = adRepository.findById(id.longValue()).get();
        return adMapper.mapperToExtendedAdDto(entity);
    }

    @Override
    public boolean removeAd(Long id) {
        AdEntity ad = adRepository.findById(id.longValue()).get();
        if (ad != null) {
            adRepository.delete(ad);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Ad updateAds(Long id, CreateOrUpdateAd dto) {
        AdEntity entity = adRepository.findById(id.longValue()).get();

        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());

        adRepository.save(entity);
        return adMapper.mapToAdDto(entity);
    }


    @Override
    public PhotoEntity updateImage(Long id, MultipartFile image) throws IOException {
        Optional<AdEntity> entity = adRepository.findById(id.longValue());
        if (entity.isPresent()) {
            PhotoEntity photo = new PhotoEntity();
            Path path = Path.of(photoDir);
            if (!path.toFile().exists()) {
                Files.createDirectories(path);
            }
            var dotIndex = Objects.requireNonNull(image.getOriginalFilename()).lastIndexOf('.');
            var ext = image.getOriginalFilename().substring(dotIndex + 1);
            var photoPath = photoDir + "/" + entity.get().getTitle() + "." + ext;
            try (var in = image.getInputStream();
                 var out = new FileOutputStream(photoPath)) {
                in.transferTo(out);
            }
            photo.setFilePath(photoPath);
            photo.setData(image.getBytes());
            photo.setFileSize(image.getSize());
            photo.setMediaType(image.getContentType());
            photo.setAd(entity.get());
            return photoRepository.save(photo);
        } else {
            return null;
        }
    }

    @Override
    public PhotoEntity findPhoto(Long id) {
        log.info("Получена фотография {}", LoggingMethodImpl.getMethodName());
        return photoRepository.findByAdId(id).get();
    }
}
