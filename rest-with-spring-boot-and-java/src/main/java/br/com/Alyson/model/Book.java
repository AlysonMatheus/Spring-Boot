package br.com.Alyson.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "books")

public class Book implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "author", nullable = false, length = 80)
    private String author;
    @Column(name = "launch_date", nullable = false)
    private Date launchDate;
    @Column(nullable = false, length = 10)
    private Double price;
    @Column(nullable = false, length = 100)
    private String title;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launch_date) {
        this.launchDate = launch_date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getLaunchDate(), book.getLaunchDate()) && Objects.equals(getPrice(), book.getPrice()) && Objects.equals(getTitle(), book.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getLaunchDate(), getPrice(), getTitle());
    }
}
