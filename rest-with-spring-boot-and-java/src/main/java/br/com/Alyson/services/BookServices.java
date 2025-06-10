package br.com.Alyson.services;

import br.com.Alyson.Controllers.BookController;
import br.com.Alyson.Controllers.PersonController;
import br.com.Alyson.Exception.RequiredObjectIsNullException;
import br.com.Alyson.Exception.ResourceNotFoundException;
import br.com.Alyson.Repository.BookRepository;
import br.com.Alyson.data.dto.books.BookDTO;

import br.com.Alyson.mapper.custom.BookMapper;
import br.com.Alyson.model.Book;
import br.com.Alyson.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.Alyson.mapper.ObjectMapper.parseListObject;
import static br.com.Alyson.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {
    private Logger logger = LoggerFactory.getLogger(BookServices.class.getName());
    @Autowired
    BookRepository repository;
    @Autowired
    BookMapper converter;

    public List<BookDTO> findAll() {
        logger.info("Finding all People!");
        var persons = parseListObject(repository.findAll(), BookDTO.class);
        persons.forEach(this::addHateoasLinks);

        return persons;
    }

    public BookDTO findById(Long id) {
        logger.info("Finding one Person!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        var dto = parseObject(entity, BookDTO.class);
        addHateoasLinks(dto);

        return dto;

    }


    public BookDTO create(BookDTO person) {
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Finding all People!");
        var entity = parseObject(person, Book.class);
        var dto = parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }



    public BookDTO update(BookDTO book) {
        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Update all People!");
        Book entity = repository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setId(book.getId());
        entity.setAuthor(book.getAuthor());
        entity.setLanchu_date(book.getLanchu_date());
        entity.setTitle(book.getTitle());
        entity.setPrice(book.getPrice());


        var dto = parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;

    }

    public void delete(Long id) {
        logger.info("Delete all People!");

        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

    private void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));

        dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));

        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));

        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }


}
}
