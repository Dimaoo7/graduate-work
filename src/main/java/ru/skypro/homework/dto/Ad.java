package ru.skypro.homework.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Ad {
    private Long id;
    private long author;
    private String description;
    private String image;
    private int price;
    private String title;

}
