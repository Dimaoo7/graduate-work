package ru.skypro.homework.dto;

import lombok.*;

@Data
public class Comment {
    private Long id;
    private long author;
    private String authorImage;
    private String authorFirstName;
    private String text;
    private long createdAt;

}
