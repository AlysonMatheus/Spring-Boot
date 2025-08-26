package br.com.Alyson.unittests.services;

import br.com.Alyson.Exception.RequiredObjectIsNullException;
import br.com.Alyson.Repository.BookRepository;
import br.com.Alyson.data.dto.books.BookDTO;
import br.com.Alyson.model.Book;
import br.com.Alyson.services.BookServices;
import br.com.Alyson.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    MockBook input;
    @InjectMocks
    private BookServices service;
    @Mock
    BookRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());


        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/book/1")
                        && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("POST"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("PUT"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("DELETE"))
        );
        assertEquals("Some Author1", result.getAuthor());
        assertEquals(25F,result.getPrice());
        assertEquals("Some Title1",result.getTitle());
        assertNotNull(result.getLaunch_date());


    }

    @Test
    void create() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);


        when(repository.save(book)).thenReturn(persisted);

        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());


        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("POST"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("PUT"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("DELETE"))
        );
        assertEquals("Some Author1", persisted.getAuthor());
        assertEquals(25F,persisted.getPrice());
        assertEquals("Some Title1",persisted.getTitle());
        assertNotNull(persisted.getLaunchDate());


    }

    @Test
    void createV2() {
    }

    @Test
    void testCreateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.create(null);
                });
        String expectMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectMessage));
    }

    @Test
    void update() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());


        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("GET"))
        );

        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("POST"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("PUT"))
        );
        assertNotNull(result.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("DELETE"))
        );
        assertEquals("Some Author1", persisted.getAuthor());
        assertEquals(25F,persisted.getPrice());
        assertEquals("Some Title1",persisted.getTitle());
        assertNotNull(persisted.getLaunchDate());


    }

    @Test
    void testUpdateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });
        String expectMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectMessage));
    }


    @Test
    void delete() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(repository);
    }

    @Test

    @Disabled("REASON: Still Under Development")
    void findAll() {
        List<Book> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<BookDTO> people = new ArrayList<>();//service.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var bookOne = people.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getId());
        assertNotNull(bookOne.getLinks());


        assertNotNull(bookOne.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("GET")));

        assertNotNull(bookOne.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("GET"))
        );

        assertNotNull(bookOne.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("POST"))
        );
        assertNotNull(bookOne.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("PUT"))
        );
        assertNotNull(bookOne.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("DELETE"))
        );
        assertEquals("Some Author1", bookOne.getAuthor());
        assertEquals(25F,bookOne.getPrice());
        assertEquals("Some Title1",bookOne.getTitle());
        assertNotNull(bookOne.getLaunch_date());




        var bookFour = people.get(4);

        assertNotNull(bookFour);
        assertNotNull(bookFour.getId());
        assertNotNull(bookFour.getLinks());


        assertNotNull(bookFour.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/book")
                        && link.getType().equals("GET")));

        assertNotNull(bookFour.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("api/book/v1")
                        && link.getType().equals("GET"))
        );

        assertNotNull(bookFour.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("api/book/v1")
                        && link.getType().equals("POST"))
        );
        assertNotNull(bookFour.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("api/book/v1")
                        && link.getType().equals("PUT"))
        );
        assertNotNull(bookFour.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("api/book/v1/4")
                        && link.getType().equals("DELETE"))
        );

        assertEquals("Some Author4", bookFour.getAuthor());
        assertEquals(25F,bookFour.getPrice());
        assertEquals("Some Title4",bookFour.getTitle());
        assertNotNull(bookFour.getLaunch_date());



        var BookSeven = people.get(7);

        assertNotNull(BookSeven);
        assertNotNull(BookSeven.getId());
        assertNotNull(BookSeven.getLinks());


        assertNotNull(BookSeven.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/book/v1/1")
                        && link.getType().equals("GET")));

        assertNotNull(BookSeven.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("api/book/v1")
                        && link.getType().equals("GET"))
        );

        assertNotNull(BookSeven.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("api/book/v1")
                        && link.getType().equals("POST"))
        );
        assertNotNull(BookSeven.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("api/book/v1")
                        && link.getType().equals("PUT"))
        );
        assertNotNull(BookSeven.getLinks().stream().
                anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("api/book/v1/7")
                        && link.getType().equals("DELETE"))
        );
        assertEquals("Some Author7", BookSeven.getAuthor());
        assertEquals(25F,BookSeven.getPrice());
        assertEquals("Some Title7",BookSeven.getTitle());
        assertNotNull(BookSeven.getLaunch_date());




    }
}