package br.com.Alyson.Controllers;

import br.com.Alyson.Controllers.docs.PersonControllerDocs;
import br.com.Alyson.data.dto.v1.PersonDTO;
import br.com.Alyson.data.dto.v2.PersonDTOV2;
import br.com.Alyson.services.PersonServices;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController implements PersonControllerDocs {

    @Autowired
    private PersonServices service;


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
   public  ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0")Integer page,
            @RequestParam(value = "size", defaultValue = "12")Integer size,
            @RequestParam(value = "direction", defaultValue = "asc")String direction
    ){
       var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortDirection,"firstName"));
        return ResponseEntity.ok(service.findAll(pageable));
    }
    @GetMapping(value = "/findPeopleByName/{firstName}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
   public  ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findByName(
           @PathVariable("firstName") String firstName,
            @RequestParam(value = "page", defaultValue = "0")Integer page,
            @RequestParam(value = "size", defaultValue = "12")Integer size,
            @RequestParam(value = "direction", defaultValue = "asc")String direction
    ){
       var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortDirection,"firstName"));
        return ResponseEntity.ok(service.findByName(firstName,pageable));
    }

    @Override
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE,
            MediaType.APPLICATION_YAML_VALUE}
    )
//@CrossOrigin(origins = "http://localhost:8081")
    public PersonDTO findById(@PathVariable("id") Long id) {
        var person = service.findById(id);

        return person;

    }

    @Override
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

//@CrossOrigin(origins = {"http://localhost:8081","https://www.alyson.com.br"})
    public PersonDTO create(@RequestBody PersonDTO person) {
        return service.create(person);

    }

    @Override
    @PostMapping(value = "/V2",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE})
    public PersonDTOV2 create(@RequestBody PersonDTOV2 person) {
        return service.createV2(person);

    }

    @Override
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE})


    public PersonDTO update(@RequestBody PersonDTO person) {
        return service.update(person);

    }

    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE,
            MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public PersonDTO disablePerson(@PathVariable("id") Long id) {
        return service.disablePerson(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")


    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
