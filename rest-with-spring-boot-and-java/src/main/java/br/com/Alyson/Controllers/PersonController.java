package br.com.Alyson.Controllers;

import br.com.Alyson.data.dto.v1.PersonDTO;
import br.com.Alyson.data.dto.v2.PersonDTOV2;
import br.com.Alyson.services.PersonServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/person/v1")
public class PersonController {

    @Autowired
    private PersonServices service;

  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,
          MediaType.APPLICATION_XML_VALUE,
          MediaType.APPLICATION_YAML_VALUE})
    public List<PersonDTO> findAll() {
        return service.findAll();

    }

     @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
             MediaType.APPLICATION_JSON_VALUE,
             MediaType.APPLICATION_XML_VALUE,
             MediaType.APPLICATION_YAML_VALUE,
             MediaType.APPLICATION_YAML_VALUE})

    public PersonDTO findById(@PathVariable("id") Long id) {
        var person = service.findById(id);

        return person;

    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_YAML_VALUE})
    public PersonDTO create(@RequestBody PersonDTO person) {
        return service.create(person);

    }
    @PostMapping( value = "/V2",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE})
    public PersonDTOV2 create(@RequestBody PersonDTOV2  person) {
        return service.createV2(person);

    }
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
    @DeleteMapping(value = "/{id}")

    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
