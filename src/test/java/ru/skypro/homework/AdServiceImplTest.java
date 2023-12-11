package ru.skypro.homework;

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
        List<Ad> adList = new ArrayList<>();

        Ad ad1 = new Ad();
        ad1.setId(1L);
        ad1.setTitle("Ad 1");
        adList.add(ad1);

        Ad ad2 = new Ad();
        ad2.setId(2L);
        ad2.setTitle("Ad 2");
        adList.add(ad2);

        when(adRepository.findAll()); //thenReturn(adList); но он хочет чтоб было List<AdEntity>

        Ads expectedAds = new Ads(1, adList);
        expectedAds.setCount(adList.size());

        Ads actualAds = adService.getAllAds();

        assertEquals(expectedAds.getCount(), actualAds.getCount());
    }
}
