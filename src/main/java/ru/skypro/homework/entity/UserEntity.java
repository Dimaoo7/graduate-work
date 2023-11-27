package ru.skypro.homework.entity;

import lombok.*;
import ru.skypro.homework.dto.Role;

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
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
    private Role role;

    @OneToOne
    private AvatarEntity avatarEntity;
    @ToString.Exclude
    @OneToMany(mappedBy = "author")
    private List<CommentEntity> commentEntities;
    @ToString.Exclude
    @OneToMany(mappedBy = "author")
    private List<AdEntity> adEntities;
}
