package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;

@Service
public class UserMapper {

    public static UserEntity mapperFromRegisterToUserEntity(Register dtoRegister) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(dtoRegister.getUsername());
        userEntity.setPassword(dtoRegister.getPassword());
        userEntity.setFirstName(dtoRegister.getFirstName());
        userEntity.setLastName(dtoRegister.getLastName());
        userEntity.setPhone(dtoRegister.getPhone());
        userEntity.setRole(dtoRegister.getRole());
        return userEntity;
    }

    public static User mapperFromUserEntityToUser(UserEntity userEntity) {
        User dtoUser = new User();
        dtoUser.setId(userEntity.getId());
        dtoUser.setEmail(userEntity.getUsername());
        dtoUser.setFirstName(userEntity.getFirstName());
        dtoUser.setLastName(userEntity.getLastName());
        dtoUser.setPhone(userEntity.getPhone());
        dtoUser.setRole(userEntity.getRole());
        dtoUser.setImage(userEntity.getAvatar().getFilePath());
        return dtoUser;
    }


    public static UpdateUser mapperFromUserEntityToUpdateUser(UserEntity userEntity) {
        UpdateUser dtoUpdateUser = new UpdateUser();
        dtoUpdateUser.setFirstName(userEntity.getFirstName());
        dtoUpdateUser.setLastName(userEntity.getLastName());
        dtoUpdateUser.setPhone(userEntity.getPhone());
        return dtoUpdateUser;
    }
}