package ru.skypro.homework.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Ad {
    private Long author;
    private String image;
    private Long pk;
    private Integer price;
    private String title;

}
