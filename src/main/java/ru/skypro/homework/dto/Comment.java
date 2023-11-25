package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private int author;
    private String authorImage;
    private String authorFirstName;
    private String text;
    private int createdAt;

}
