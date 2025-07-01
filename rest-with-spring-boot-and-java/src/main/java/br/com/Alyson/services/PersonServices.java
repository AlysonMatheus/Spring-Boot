package br.com.Alyson.services;

import br.com.Alyson.Controllers.PersonController;
import br.com.Alyson.Exception.RequiredObjectIsNullException;
import br.com.Alyson.Exception.ResourceNotFoundException;
import br.com.Alyson.Repository.PersonRepository;
import br.com.Alyson.data.dto.v1.PersonDTO;

import static br.com.Alyson.mapper.ObjectMapper.parseListObject;
import static br.com.Alyson.mapper.ObjectMapper.parseObject;

import br.com.Alyson.data.dto.v2.PersonDTOV2;
import br.com.Alyson.mapper.custom.PersonMapper;
import br.com.Alyson.model.Person;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class PersonServices {

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());


    @Autowired
    PersonRepository repository;
    @Autowired
    PersonMapper converter;


    public List<PersonDTO> findAll() {
        logger.info("Finding all People!");
        var persons = parseListObject(repository.findAll(), PersonDTO.class);
        persons.forEach(this::addHateoasLinks);

        return persons;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);

        return dto;

    }


    public PersonDTO create(PersonDTO person) {
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Finding all People!");
        var entity = parseObject(person, Person.class);
        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTOV2 createV2(PersonDTOV2 person) {
        logger.info("Finding all People!");
        var entity = converter.convertDTOtoEntity(person);
        return converter.convertEntityToDTO(repository.save(entity));
    }

    public PersonDTO update(PersonDTO person) {
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Update all People!");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());


        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;

    }
    @Transactional
    public PersonDTO disablePerson(Long id) {
        logger.info("Disable Person!");

         repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.disablePerson(id);
        var entity = repository.findById(id).get();
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;

    }
    public void delete(Long id) {
        logger.info("Delete all People!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));

        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto)).withRel("update").withType("PATCH"));

        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }


}
