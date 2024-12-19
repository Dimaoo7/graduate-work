package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.impl.LoggingMethodImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.io.IOException;

@Slf4j
@RestController
@Tag(name = "\uD83D\uDE48 Пользователи")
@CrossOrigin("http://localhost:3000")
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(
            tags = "Пользователи",
            summary = "Получение информации об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не авторизован",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/me")
    public ResponseEntity<User> getUser(Authentication authentication) {
        log.info("Method {}", LoggingMethodImpl.getMethodName());
        UserEntity userEntity = userService.getUser(authentication.getName());
        if (userEntity != null) {
            return ResponseEntity.ok(UserMapper.mapperFromUserEntityToUser(userEntity));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @Operation(
            tags = "Пользователи",
            summary = "Изменение данных пользователя",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Пользователь обновлен",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UpdateUser.class))),
                @ApiResponse(responseCode = "401",
                        description = "Пользователь не авторизован",
                        content = @Content())
            }
    )
    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser, Authentication authentication) {
        log.info("Method {}", LoggingMethodImpl.getMethodName());
        UserEntity userEntity = userService.updateUser(updateUser, authentication);
        if (userEntity != null) {
            return ResponseEntity.ok(UserMapper.mapperFromUserEntityToUpdateUser(userEntity));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            tags = "Пользователи",
            summary = "Обновление пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пароль обновлен",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не авторизован",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Доступ запрещен",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден",
                            content = @Content()
                    )
            }
    )
    @PostMapping("/set_password")
    public ResponseEntity<User> setPassword(@RequestBody NewPassword newPassword, Authentication authentication) {
        log.info("Method {}", LoggingMethodImpl.getMethodName());
        userService.setPassword(newPassword, authentication);
        return ResponseEntity.ok().build();
    }
    @Operation(
            tags = "Пользователи",
            summary = "Обновление аватара авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Картинка загружена",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не авторизован (unauthorized)",
                            content = @Content()
                    )
            }
    )
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam MultipartFile image,
                                                Authentication authentication) throws IOException {
        log.info("За запущен метод контроллера: {}", LoggingMethodImpl.getMethodName());
        userService.updateUserImage(image, authentication);
        return ResponseEntity.ok().build();
    }
}
