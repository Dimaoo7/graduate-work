package ru.skypro.homework.dto;

import lombok.*;

@Data
public class ExtendedAd extends Ad {
    private Long pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private Integer price;
    private String title;
}
