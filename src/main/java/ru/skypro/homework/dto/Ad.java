package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class Ad {
    private Long id;
    private int author;
    private String description;
    private String image;
    private int price;
    private String title;
}
