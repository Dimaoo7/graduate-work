package ru.skypro.homework.dto;

import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateOrUpdateAd {
    private Long id;

    private String description; // описание объявления

    private String image; // ссылка на картинку объявления

    private Integer price; // цена объявления
    private String title; // заголовок объявления
}
