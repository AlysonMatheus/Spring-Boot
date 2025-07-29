package br.com.Alyson.services;

import br.com.Alyson.Controllers.PersonController;
import br.com.Alyson.Exception.BadReuqestException;
import br.com.Alyson.Exception.FileStorageException;
import br.com.Alyson.Exception.RequiredObjectIsNullException;
import br.com.Alyson.Exception.ResourceNotFoundException;
import br.com.Alyson.Repository.PersonRepository;
import br.com.Alyson.data.dto.v1.PersonDTO;


import static br.com.Alyson.mapper.ObjectMapper.parseObject;

import br.com.Alyson.data.dto.v2.PersonDTOV2;
import br.com.Alyson.file.importer.contract.FileImporter;
import br.com.Alyson.file.importer.factory.FileImporterFactory;
import br.com.Alyson.mapper.custom.PersonMapper;
import br.com.Alyson.model.Person;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;


@Service
public class PersonServices {

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());


    @Autowired
    PersonRepository repository;
    @Autowired
    PersonMapper converter;
    @Autowired
    FileImporterFactory importer;

    @Autowired
    PagedResourcesAssembler<PersonDTO> assembler;


    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable) {
        logger.info("Finding all People!");
        var people = repository.findAll(pageable);
        return buildPageModel(pageable, people);
    }

    public PagedModel<EntityModel<PersonDTO>> findByName(String firstName, Pageable pageable) {
        logger.info("Finding People by name!");
        var people = repository.findPeopleByName(firstName, pageable);
        return buildPageModel(pageable, people);
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
   // verifica se o multipartiFile esta preenchido se não ele lança uma execeção

    public List<PersonDTO> massCreation(MultipartFile file) throws IOException {
        logger.info("Importing People from file!");
        if (file.isEmpty()) throw new BadReuqestException("Please set a Valid File!");
        try (InputStream inputStream = file.getInputStream())// passa o nome de qual ele deve utilizar na factory
        {
            String filename = Optional.ofNullable(file.getOriginalFilename()).orElseThrow(() -> new BadReuqestException("File name cannot be null"));
            FileImporter importer = this.importer.getImporter(filename);
            List<Person> entites = importer.importFile(inputStream).stream()// chama o importador e manda importar, passando o input stream
                    .map(dto -> repository.save(parseObject(dto, Person.class))).toList();// retorna uma lista de personDTO, converte e salva no banco(essa lista nao pode ser devolvida como resposta)
            return entites.stream()// entao ele intera nessa lista de person, passando de entidade para dto e adcionando links hateoas e no final converte em uma lista e devolve a resposta
                    .map(entity -> {
                        var dto = parseObject(entity, PersonDTO.class);
                        addHateoasLinks(dto);
                        return dto;
                    })
                    .toList();


        } catch (Exception e){
            throw  new FileStorageException("Error processing the file!");
        }

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

    private PagedModel<EntityModel<PersonDTO>> buildPageModel(Pageable pageable, Page<Person> people) {
        var peopleWithLinks = people.map(person -> {
            var dto = parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });
        Link findallLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PersonController.class).findAll(
                        pageable.getPageNumber(),
                        pageable.getPageSize(), String.valueOf(pageable.getSort()))).withSelfRel();
        return assembler.toModel(peopleWithLinks, findallLink);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findByName("", 1, 12, "asc")).withRel("findByName").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("update").withType("PATCH"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }


}
