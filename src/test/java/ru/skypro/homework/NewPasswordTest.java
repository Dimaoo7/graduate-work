package ru.skypro.homework;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.NewPassword;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewPasswordTest {
    @Test
    public void testGetAndSet() {
        NewPassword newPassword = new NewPassword();
        newPassword.setCurrentPassword("oldPassword");
        newPassword.setNewPassword("newPassword");

        assertEquals("oldPassword", newPassword.getCurrentPassword());
        assertEquals("newPassword", newPassword.getNewPassword());
    }
}
