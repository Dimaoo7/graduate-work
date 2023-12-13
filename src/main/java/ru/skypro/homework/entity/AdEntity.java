package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ads")
public class AdEntity {
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
    private List<CommentEntity> commentEntities;
    @OneToOne
    private PhotoEntity photo;

    private String image;
}
