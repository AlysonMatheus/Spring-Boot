package br.com.Alyson.unitetests.mapper.mocks;

import br.com.Alyson.data.dto.books.BookDTO;
import br.com.Alyson.model.Book;
import br.com.Alyson.model.Person;

import java.util.ArrayList;
import java.util.List;


public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookDTO mockDTO() {
        return mockDTO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setTitle("Address Test" + number);
        book.setAuthor("First Name Test" + number);
        book.setId(number.longValue());
        book.setPrice(number);
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setTitle("Address Test" + number);
        book.setAuthor("First Name Test" + number);
        book.setId(number.longValue());
        book.setPrice(number);
        return book;

    }

}