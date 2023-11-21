package ru.skypro.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;

import static ru.skypro.homework.dto.Role.USER;

@SpringBootTest
public class MapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void userToUserTest() {
        //given
        UserEntity userEntity = new UserEntity("1", "000", "1", "2", USER, "5");

        //when
        User user = UserMapper.INSTANCE.userToUser(userEntity);

        //then
        Assertions.assertNotNull(user);
        Assertions.assertEquals(userEntity.getFirstName(), user.getFirstName());
        Assertions.assertEquals(userEntity.getLastName(), user.getLastName());
        Assertions.assertEquals(userEntity.getPhone(), user.getPhone());
        Assertions.assertEquals(userEntity.getEmail(), user.getEmail());
        Assertions.assertEquals(userEntity.getRole(), user.getRole());
        Assertions.assertEquals(userEntity.getImage(), user.getImage());
    }
}
