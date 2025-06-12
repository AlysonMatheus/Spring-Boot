package br.com.Alyson.data.dto.books;

import br.com.Alyson.data.dto.v1.PersonDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {
    private static final long serialVersionUID = 1l;
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String author;

    private Date launch_date;

    private float price;

    private String title;

    public BookDTO(){

    }

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

    public Date getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(Date launch_date) {
        this.launch_date = launch_date;
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
        if (!super.equals(o)) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Float.compare(getPrice(), bookDTO.getPrice()) == 0 && Objects.equals(getId(), bookDTO.getId()) && Objects.equals(getAuthor(), bookDTO.getAuthor()) && Objects.equals(getLaunch_date(), bookDTO.getLaunch_date()) && Objects.equals(getTitle(), bookDTO.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getAuthor(), getLaunch_date(), getPrice(), getTitle());
    }
}
