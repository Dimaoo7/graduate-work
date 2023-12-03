package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
import ru.skypro.homework.service.UserService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final PhotoRepository photoRepository;
    private final AdMapper adMapper;
    private final UserService userService;
    private final String photoDir;



    public AdServiceImpl(AdRepository adRepository,
                         PhotoRepository photoRepository,
                         AdMapper adMapper,
                         UserService userService,
                         @Value("${path.to.photos.folder}") String photoDir) {
        this.adRepository = adRepository;
        this.photoRepository = photoRepository;
        this.adMapper = adMapper;
        this.userService = userService;
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
    public Ad addAd() throws IOException {
        AdEntity adEntity = new AdEntity();
        adEntity.setTitle(properties.getTitle());
        adEntity.setPrice(properties.getPrice());
        adEntity.setDescription(properties.getDescription());

        PhotoEntity photo = new PhotoEntity();
        Path path = Path.of(photoDir);
        if (!path.toFile().exists()) {
            Files.createDirectories(path);
        }
        var dotIndex = Objects.requireNonNull(image.getOriginalFilename()).lastIndexOf('.');
        var ext = image.getOriginalFilename().substring(dotIndex + 1);
        var photoPath = photoDir + "/" + properties.getTitle() + "." + ext;
        try (var in = image.getInputStream();
             var out = new FileOutputStream(photoPath)) {
            in.transferTo(out);
        }
        photo.setFilePath(photoPath);
        photo.setData(image.getBytes());
        photo.setFileSize(image.getSize());
        photo.setMediaType(image.getContentType());

        adEntity.setImage((PhotoEntity) image);
        adEntity.setAuthor(userService.getUser());

        photo.setAd(adEntity);

        photoRepository.save(photo);
        adRepository.save(adEntity);
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
}
