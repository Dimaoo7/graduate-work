package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.impl.AuthServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;
    Authentication authentication;
    AuthServiceImpl authService;

    public UserController(UserService userService, AuthServiceImpl authService) {
        this.userService = userService;
        this.authService = authService;
    }


    @GetMapping("/me")
    public ResponseEntity<User> getUser() {
        UserEntity userEntity = userService.getUser();
        if (userEntity != null) {
            return ResponseEntity.ok(UserMapper.mapperFromUserEntityToUser(userEntity));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser) {
        UserEntity userEntity = userService.updateUser(updateUser);
        if (userEntity != null) {
            return ResponseEntity.ok(UserMapper.mapperFromUserEntityToUpdateUser(userEntity));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping("/set_password")
    public ResponseEntity<User> setPassword(@RequestBody NewPassword newPassword) {
        userService.setPassword(newPassword);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity <User> checkUserByUsername(@RequestParam String userName) {
        UserEntity userEntity = userService.checkUserByUsername(userName);
        return ResponseEntity.ok(UserMapper.mapperFromUserEntityToUser(userEntity));

    }
    //   @PatchMapping("/users/me/image")
    //   public User userMeImage (@PathVariable User image){
    //       return image; //возвращает пустое значение поля image
    //   }
}
