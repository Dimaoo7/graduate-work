package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.PhotoEntity;
import ru.skypro.homework.repository.PhotoRepository;

import java.io.IOException;
import java.util.Objects;

@Service
public class AdMapper {

    private final PhotoRepository photoRepository;

    public AdMapper(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Ad mapToAdDto(AdEntity adEntity) {
        Ad dtoAd = new Ad();
        dtoAd.setId(adEntity.getId());
        dtoAd.setAuthor(adEntity.getAuthor().getId());
        dtoAd.setDescription(adEntity.getDescription());
        dtoAd.setImage(adEntity.getImage());
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
        dtoExtendedAd.setImage(adEntity.getImage());
        dtoExtendedAd.setPrice(adEntity.getPrice());
        dtoExtendedAd.setTitle(adEntity.getImage());
        return dtoExtendedAd;
    }

    public PhotoEntity mapToMultipartPhoto(MultipartFile image) throws IOException {

        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setFileSize(image.getSize());
        photoEntity.setMediaType(image.getContentType());
        photoEntity.setData(Objects.requireNonNull(image.getBytes()));
        photoRepository.save(photoEntity);
        return photoEntity;
    }
}
