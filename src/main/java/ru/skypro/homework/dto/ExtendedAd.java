package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class ExtendedAd extends Ad {
    private Long id;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String phone;
    private String email;
    private String image;
    private int price;
    private String title;



}
