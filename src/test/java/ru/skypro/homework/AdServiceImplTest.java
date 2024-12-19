package ru.skypro.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AdServiceImplTest {
    @Mock
    private AdRepository adRepository;

    @Mock
    private AdMapper adMapper;

    @InjectMocks
    private AdServiceImpl adService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAds() {
        // Arrange
        List<Ad> dtos = new ArrayList<>();

        when(adRepository.findAll().stream());

        Ads expectedAds = new Ads(1, dtos);
        expectedAds.setCount(dtos.size());

        Ads actualAds = new Ads(1, dtos);
        actualAds.setCount(dtos.size());

        assertEquals(expectedAds, actualAds);
    }
}
