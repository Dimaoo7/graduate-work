package ru.skypro.homework.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.PhotoEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.PhotoRepository;
import ru.skypro.homework.repository.UserRepository;

import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AdMapper {

    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;



    public Ad mapToAdDto(AdEntity adEntity) {
        Ad dtoAd = new Ad();
        dtoAd.setPk(adEntity.getId());
        dtoAd.setAuthor(adEntity.getAuthor().getId());
        dtoAd.setImage(adEntity.getImage());
        dtoAd.setPrice(adEntity.getPrice());
        dtoAd.setTitle(adEntity.getTitle());
        return dtoAd;
    }


    public ExtendedAd mapToExtendedAdDto(AdEntity entity) {
        ExtendedAd dto = new ExtendedAd();
        dto.setPk(entity.getId());
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
    public AdEntity mapToAdEntity(CreateOrUpdateAd dto, String username) {
        UserEntity author = userRepository.findUserEntityByUserName(username);
        if (author == null) {
            throw new UserNotFoundException("User not found");
        }
        AdEntity entity = new AdEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setAuthor(author);
        return entity;
    }
}
