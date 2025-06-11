package br.com.Alyson.mapper.custom;

import br.com.Alyson.data.dto.books.BookDTO;
import br.com.Alyson.model.Book;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public BookDTO convertEntityToDTO(Book book){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setTitle(book.getTitle());
      
        bookDTO.setPrice(book.getPrice());
        return bookDTO;

    }

}
