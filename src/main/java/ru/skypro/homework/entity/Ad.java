
package ru.skypro.homework.entity;

import ru.skypro.homework.dto.Comment;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    private List<Comment> comments;
    private String image;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ad() {
    }

    @Override
    public String toString() {
        return "Ad{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                ", comments=" + comments +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return price == ad.price && Objects.equals(title, ad.title) && Objects.equals(description, ad.description) && Objects.equals(author, ad.author) && Objects.equals(comments, ad.comments) && Objects.equals(image, ad.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, author, comments, image, price);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Ad(String title, String description, User author, List<Comment> comments, String image, int price) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.comments = comments;
        this.image = image;
        this.price = price;
    }
}