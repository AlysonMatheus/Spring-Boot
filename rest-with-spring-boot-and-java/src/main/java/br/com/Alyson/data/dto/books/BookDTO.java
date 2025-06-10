package br.com.Alyson.data.dto.books;

import br.com.Alyson.data.dto.v1.PersonDTO;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {
    private static final long serialVersionUID = 1l;
    private int id;

    private String author;

    private Date lanchu_date;

    private float price;

    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLanchu_date() {
        return lanchu_date;
    }

    public void setLanchu_date(Date lanchu_date) {
        this.lanchu_date = lanchu_date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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
        BookDTO bookDTO = (BookDTO) o;
        return getId() == bookDTO.getId() && Float.compare(getPrice(), bookDTO.getPrice()) == 0 && Objects.equals(getAuthor(), bookDTO.getAuthor()) && Objects.equals(getLanchu_date(), bookDTO.getLanchu_date()) && Objects.equals(getTitle(), bookDTO.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getLanchu_date(), getPrice(), getTitle());
    }
}
