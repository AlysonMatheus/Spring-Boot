package br.com.Alyson.Repository;

import br.com.Alyson.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
