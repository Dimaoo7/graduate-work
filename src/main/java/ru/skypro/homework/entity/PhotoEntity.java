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
@Table(name = "photos")
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String filePath;
    private long fileSize;
    private String contentType;
    @Lob
    private byte[] data;
    @OneToOne
    private AdEntity adEntity;

}
