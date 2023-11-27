package ru.skypro.homework.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Comment {
    private Long id;
    private long author;
    private String authorImage;
    private String authorFirstName;
    private String text;
    private long createdAt;

}
