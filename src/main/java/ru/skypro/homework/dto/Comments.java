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
public class Comments {
    private int count;
    private List<Comment> results;
}
