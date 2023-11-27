package ru.skypro.homework.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Ads {
    private int count;
    private List<Ad> results;
}
