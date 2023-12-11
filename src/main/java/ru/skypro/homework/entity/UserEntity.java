package ru.skypro.homework.entity;

import lombok.*;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends ModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

    @OneToOne
    private PhotoEntity photo;

    @OneToMany(mappedBy = "author")
    private Collection<AdEntity> ads;

    @OneToMany(mappedBy = "author")
    private Collection<CommentEntity> comments;

    private String filePath;

}