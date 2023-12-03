package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;

import java.io.IOException;


@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdsController {
    AdService adService;

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

    @PostMapping
    public ResponseEntity<Ad> createAd(@RequestParam CreateOrUpdateAd properties, MultipartFile image) throws IOException {
        Ad ad = adService.addAd();
        return ResponseEntity.ok(ad);
    }

///    @DeleteMapping("/{id}")
///    public ResponseEntity deleteAd(@PathVariable ("Id") Long id) {
///
///
///    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable("Id)") Long id, @RequestBody CreateOrUpdateAd dto) {
        Ad ad = adService.updateAds(id, dto);
        if (ad != null) {
            return ResponseEntity.ok(ad);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}
        }

}


///    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
///
///    public ResponseEntity<PhotoEntity> updateAdImage(@PathVariable ("Id") Long id, @RequestParam MultipartFile image)  {
///        return new CreateOrUpdateAd(); // Возвращает пустой объект createOrUpdateAd
///    }

