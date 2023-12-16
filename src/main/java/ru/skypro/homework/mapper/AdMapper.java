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

    public ExtendedAd mapToExtendedAdDto(AdEntity entity) {
        ExtendedAd dto = new ExtendedAd();
        dto.setId(entity.getId());
        dto.setAuthorFirstName(entity.getAuthor().getFirstName());
        dto.setAuthorLastName(entity.getAuthor().getLastName());
        dto.setDescription(entity.getDescription());
        dto.setEmail(entity.getAuthor().getUserName());
        dto.setImage(entity.getImage());
        dto.setPhone(entity.getAuthor().getPhone());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        return dto;
    }

    public PhotoEntity mapToMultipartPhoto(MultipartFile image) throws IOException {

        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setFileSize(image.getSize());
        photoEntity.setMediaType(image.getContentType());
        photoEntity.setData(Objects.requireNonNull(image.getBytes()));
        return photoEntity;
    }
}
