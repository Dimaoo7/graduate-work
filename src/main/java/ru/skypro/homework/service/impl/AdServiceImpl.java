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
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.PhotoRepository;
import ru.skypro.homework.service.AdService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final PhotoRepository photoRepository;
    private final AdMapper adMapper;
    private final ImageServiceImpl imageService;
    private final UserServiceImpl userService;
    @Value("${path.to.photos.folder}")
    private String photoDir;

    public AdServiceImpl(AdRepository adRepository,
                         PhotoRepository photoRepository,
                         AdMapper adMapper,
                         ImageServiceImpl imageService,
                         UserServiceImpl userService) {
        this.adRepository = adRepository;
        this.photoRepository = photoRepository;
        this.adMapper = adMapper;
        this.imageService = imageService;
        this.userService = userService;
    }


    @Override
    public Ads getAllAds() {
        List<Ad> dtos = adRepository.findAll().stream()
                .map(adMapper::mapToAdDto)
                .collect(Collectors.toList());
        return new Ads(dtos.size(), dtos);
    }


    @Override
    public Ad addAd(CreateOrUpdateAd properties,
                    MultipartFile image,
                    Authentication authentication) throws IOException {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());

        AdEntity adEntity = new AdEntity();


        adEntity.setTitle(properties.getTitle());
        adEntity.setPrice(properties.getPrice());
        adEntity.setDescription(properties.getDescription());


        adEntity.setAuthor(userService.getUser(authentication.getName()));

        adEntity = (AdEntity) imageService.updateEntitiesPhoto(image, adEntity);
        log.info("Сущность adEntity сформированная в {}", LoggingMethodImpl.getMethodName());


        adRepository.save(adEntity);


        return adMapper.mapToAdDto(adEntity);
    }



    @Override
    public ExtendedAd getAds(Long id) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        AdEntity entity = adRepository.findById(id).get();
        return adMapper.mapperToExtendedAdDto(entity);
    }

    @Transactional
    @Override
    public boolean removeAd(Long id) throws IOException {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        AdEntity ad = adRepository.findById(id).get();
        if (ad != null) {

            adRepository.delete(ad);
            photoRepository.delete(ad.getPhoto());
            String filePath = ad.getFilePath();
            Path path = Path.of(filePath);
            Files.delete(path);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public Ad updateAds(Long id, CreateOrUpdateAd dto) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        AdEntity entity = adRepository.findById(id).get();

        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());

        adRepository.save(entity);
        return adMapper.mapToAdDto(entity);
    }

    @Override
    @Transactional
    public Ads getAdsMe(String username) {
        log.info("Method {}", LoggingMethodImpl.getMethodName());
        UserEntity author = userService.getUser(username);
        List<Ad> ads = null;
        ads = adRepository.findByAuthor(author).stream()
                .map(adMapper::mapToAdDto)
                .collect(Collectors.toList());
        return new Ads(ads.size(), ads);
    }

    @Transactional
    @Override
    public void updateImage(Long id, MultipartFile image) throws IOException {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        AdEntity adEntity = adRepository.findById(id).orElseThrow(RuntimeException::new);
        adEntity = (AdEntity) imageService.updateEntitiesPhoto(image, adEntity);
        log.info("adEntity cоздано - {}", adEntity != null);
        adRepository.save(adEntity);
    }

    public boolean isAuthorAd(String username, Long adId) {
        log.info("Использован метод сервиса: {}", LoggingMethodImpl.getMethodName());

        AdEntity adEntity = adRepository.findById(adId).orElseThrow(RuntimeException::new);
        return adEntity.getAuthor().getUserName().equals(username);
    }

}