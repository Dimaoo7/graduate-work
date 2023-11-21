
package ru.skypro.homework.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Comment {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    @Column(name = "id",nullable = false)
    private Long id;
    private String text;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;
    private long createdAt;
    private String authorFirstName;
    private String authorImage;

    public Comment() {
    }


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", createdAt=" + createdAt +
                ", authorFirstName='" + authorFirstName + '\'' +
                ", authorImage='" + authorImage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return createdAt == comment.createdAt && Objects.equals(id, comment.id) && Objects.equals(text, comment.text) && Objects.equals(author, comment.author) && Objects.equals(authorFirstName, comment.authorFirstName) && Objects.equals(authorImage, comment.authorImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, author, createdAt, authorFirstName, authorImage);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public Comment(String text, UserEntity author, int createdAt, String authorFirstName, String authorImage) {
        this.text = text;
        this.author = author;
        this.createdAt = createdAt;
        this.authorFirstName = authorFirstName;
        this.authorImage = authorImage;
    }

}