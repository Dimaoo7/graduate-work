package ru.skypro.homework.dto;

import lombok.*;

@Data
public class Ad {
    private Long id;
    private long author;
    private String description;
    private String image;
    private int price;
    private String title;

}
