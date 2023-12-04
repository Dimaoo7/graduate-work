package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.AvatarService;

import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    AvatarService avatarService;
    AuthService authService;

    public AvatarController(AvatarService avatarService, AuthService authService) {
        this.avatarService = avatarService;
        this.authService = authService;
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<String> updateAvatar(@PathVariable("id") Long id,
                                               @RequestParam MultipartFile image,
                                               Authentication authentication) throws IOException {
        avatarService.updateAvatar(id, image, authentication);
        return ResponseEntity.ok().build();
    }
}

