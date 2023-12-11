package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

import java.io.IOException;

public interface AdService {
    Ads getAllAds();

    Ad addAd(CreateOrUpdateAd properties, MultipartFile image, Authentication authentication) throws IOException;

    ExtendedAd getAds(Long id);

    boolean removeAd(Long id) throws IOException;

    Ad updateAds(Long id, CreateOrUpdateAd dto);

    Ads getAdsMe(String username);

    void updateImage(Long id, MultipartFile image) throws IOException;
}
