package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;

@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdsController {

    @GetMapping
    public ExtendedAd getAdById(@PathVariable int id) {
        return new ExtendedAd(); // Возвращает пустой объект ExtendedAd
    }

    @PostMapping
    public CreateOrUpdateAd createAd(@RequestBody CreateOrUpdateAd createOrUpdateAd) {
        return new CreateOrUpdateAd(); // Возвращает пустой объект CreateOrUpdateAd
    }

    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable int id) {
        return;
    }
    @PatchMapping("/{id}")
    public CreateOrUpdateAd updateAd(@PathVariable int id, @PathVariable CreateOrUpdateAd title, @PathVariable CreateOrUpdateAd price,
                                     @PathVariable CreateOrUpdateAd description) {
        return new CreateOrUpdateAd(); // Возвращает пустой объект createOrUpdateAd
    }
    @PatchMapping("/{id}/image")
    public CreateOrUpdateAd updateAdImage (@PathVariable int id, @PathVariable CreateOrUpdateAd image) {
        return new CreateOrUpdateAd(); // Возвращает пустой объект createOrUpdateAd
    }

}