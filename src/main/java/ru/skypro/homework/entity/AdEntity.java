package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ads")
public class AdEntity extends ModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int price;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;
    @OneToMany(mappedBy = "adEntity")
    private Collection<CommentEntity> commentEntities;
    @OneToOne
    private PhotoEntity photo;

    private String image;
    private String filePath;


}
