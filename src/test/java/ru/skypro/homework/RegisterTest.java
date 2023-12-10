package ru.skypro.homework;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterTest {
    @Test
    public void testGetAndSet() {
        Register register = new Register();
        register.setUsername("testUser");
        register.setPassword("testPassword");
        register.setFirstName("Stepan");
        register.setLastName("Orlov");
        register.setPhone("79274669751");
        register.setRole(Role.USER);

        assertEquals("testUser", register.getUsername());
        assertEquals("testPassword", register.getPassword());
        assertEquals("Stepan", register.getFirstName());
        assertEquals("Orlov", register.getLastName());
        assertEquals("79274669751", register.getPhone());
        assertEquals(Role.USER, register.getRole());
    }
}
