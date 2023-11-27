package ru.skypro.homework.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewPassword {

    private String password;

    private String newPassword;
}
