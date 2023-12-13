package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.PhotoEntity;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.impl.LoggingMethodImpl;

import java.io.IOException;


@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RestController
@Slf4j
public class AdsController {
    private final AdService adService;

    public AdsController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping
    public ResponseEntity<Ads> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @GetMapping("/{Id}")
    public ResponseEntity<ExtendedAd> getAdById(@PathVariable("Id") Long id) {
        ExtendedAd extendedAd = adService.getAds(id);
        if (extendedAd != null) {
            return ResponseEntity.ok(extendedAd);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @Operation(
            summary = "Добавление объявления",
            tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Ad.class))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden"),
            @ApiResponse(responseCode = "404",
                    description = "Not Found")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Ad> addAd(@RequestPart(value = "properties", required = false) CreateOrUpdateAd properties,
                                    @RequestPart("image") MultipartFile image,
                                    Authentication authentication) throws IOException {
        log.info("Объявление добавлено {}", LoggingMethodImpl.getMethodName());
        return ResponseEntity.ok(adService.addAd(properties, image, authentication));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable("Id)") Long id, @RequestBody CreateOrUpdateAd dto) {
        Ad ad = adService.updateAds(id, dto);
        if (ad != null) {
            return ResponseEntity.ok(ad);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @AdServiceImpl.isAuthorAd(authentication.name, #adId)")
    public ResponseEntity<Void> deleteAd(@PathVariable("id") Long id) {
        log.info("Объявление удалено {}", LoggingMethodImpl.getMethodName());
        return (adService.removeAd(id)) ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN') or @adServiceImpl.isAuthorAd(authentication.name, #adId)")
    public ResponseEntity<byte[]> updateImage(@PathVariable("id") Long adId,
                                              @RequestParam MultipartFile image) throws IOException {
        log.info("Объявление обновлено: {}", LoggingMethodImpl.getMethodName());

        adService.updateImage(adId, image);

        PhotoEntity photo = adService.findPhoto(adId);
        if (photo != null) {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(photo.getMediaType()));
            headers.setContentLength(photo.getData().length);

            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(photo.getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

