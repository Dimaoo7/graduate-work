package ru.skypro.homework.dto;

import lombok.*;
@Data
public class CreateOrUpdateAd {
    private Long id;

    private String description; // описание объявления

    private String image; // ссылка на картинку объявления

    private Integer price; // цена объявления
    private String title; // заголовок объявления
}
