package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

import java.io.IOException;

public interface AdService {
    /**
     * Получает все объявления
     * @return Ads - полный списк объявлений
     */
    Ads getAllAds();
    /**
     * Добавляет объявление в базу
     * @param properties создание объявления
     * @param image изображение товара
     * @param authentication идентификация пользователя
     * @throws IOException если не добавилось объявление в базу
     * @return Ad - объявление
     */
    Ad addAd(CreateOrUpdateAd properties, MultipartFile image, Authentication authentication) throws IOException;
    /**
     * Получение информации об объявлении
     * @param id ID объявления
     * @return ExtendedAd - получение информации
     */
    ExtendedAd getAds(Long id);
    /**
     * Удаление информации об объявлении
     * @param id ID объявления
     * @throws IOException если информация об объявлении не удалилась
     * @return true при успешном удалении, иначе false
     */
    boolean removeAd(Long id) throws IOException;
    /**
     * Обновление информации об объявлении
     * @param id ID объявления
     * @param dto - dto для обновления объявления
     * @return Ad- обновленное объявление
     */
    Ad updateAds(Long id, CreateOrUpdateAd dto);
    /**
     * Получение объявлений авторизованного пользователя
     * @param username имя пользователя
     * @return Ads - передача полного списка объявлений
     */
    Ads getAdsMe(String username);
    /**
     * Обновление изображения
     * @param id ID изображения
     * @param image - полученный MultipartFile
     * @throws IOException если изображение не обновилось
     */
    void updateImage(Long id, MultipartFile image) throws IOException;
}
