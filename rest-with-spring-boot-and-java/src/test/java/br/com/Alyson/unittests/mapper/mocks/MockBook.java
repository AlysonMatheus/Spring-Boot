package br.com.Alyson.unittests.mapper.mocks;

import br.com.Alyson.data.dto.books.BookDTO;
import br.com.Alyson.model.Book;


import java.util.Date;
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
        book.setId(number.longValue());
        book.setAuthor("Some Author" + number);
        book.setLaunch_date(new Date());
        book.setPrice(25F);
        book.setTitle("Some Title" + number);
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setId(number.longValue());
        book.setAuthor("Some Author" + number);
        book.setLaunch_date(new Date());
        book.setPrice(25F);
        book.setTitle("Some Title" + number);
        return book;
    }

}