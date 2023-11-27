package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "avatars")
public class AvatarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;
    private long fileSize;
    private String contentType;

    @Lob
    private byte[] data;

    @OneToOne
    private UserEntity userEntity;

}
