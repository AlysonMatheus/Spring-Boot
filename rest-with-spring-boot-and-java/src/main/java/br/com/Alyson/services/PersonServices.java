package br.com.Alyson.services;

import br.com.Alyson.Exception.ResourceNotFoundException;
import br.com.Alyson.Repository.PersonRepository;
import br.com.Alyson.data.dto.PersonDTO;

import static br.com.Alyson.mapper.ObjectMapper.parseListObject;
import static br.com.Alyson.mapper.ObjectMapper.parseObject;

import br.com.Alyson.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class PersonServices {
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());


    @Autowired
    PersonRepository repository;


    public List<PersonDTO> findAll() {
        logger.info("Finding all People!");
        return parseListObject(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return parseObject(entity, PersonDTO.class);

    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Finding all People!");
        var entity = parseObject(person, Person.class);
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Update all People!");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());


        return parseObject(repository.save(entity), PersonDTO.class);

    }

    public void delete(Long id) {
        logger.info("Delete all People!");
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }


}
