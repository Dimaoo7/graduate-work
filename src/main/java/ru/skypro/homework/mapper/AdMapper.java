package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;

@Service
public class AdMapper {

     public Ad mapToAdDto(AdEntity adEntity) {
        Ad dtoAd = new Ad();
        dtoAd.setId(adEntity.getId());
        dtoAd.setAuthor(adEntity.getAuthor().getId());
        dtoAd.setDescription(adEntity.getDescription());
        dtoAd.setImage(adEntity.getImage().getFilePath());
        dtoAd.setPrice(adEntity.getPrice());
        dtoAd.setTitle(adEntity.getTitle());
        return dtoAd;
    }

    public ExtendedAd mapperToExtendedAdDto(AdEntity adEntity) {
        ExtendedAd dtoExtendedAd = new ExtendedAd();
        dtoExtendedAd.setId(adEntity.getId());
        dtoExtendedAd.setAuthorFirstName(adEntity.getAuthor().getFirstName());
        dtoExtendedAd.setAuthorLastName(adEntity.getAuthor().getLastName());
        dtoExtendedAd.setDescription(adEntity.getDescription());
        dtoExtendedAd.setPhone(adEntity.getAuthor().getPhone());
        dtoExtendedAd.setEmail(adEntity.getAuthor().getUserName());
        dtoExtendedAd.setImage(adEntity.getImage().getFilePath());
        dtoExtendedAd.setPrice(adEntity.getPrice());
        dtoExtendedAd.setTitle(adEntity.getImage().getFilePath());
        return dtoExtendedAd;
    }
}
