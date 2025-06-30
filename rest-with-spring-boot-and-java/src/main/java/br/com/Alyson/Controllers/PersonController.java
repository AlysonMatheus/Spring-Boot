package br.com.Alyson.Controllers;

import br.com.Alyson.Controllers.docs.PersonControllerDocs;
import br.com.Alyson.data.dto.v1.PersonDTO;
import br.com.Alyson.data.dto.v2.PersonDTOV2;
import br.com.Alyson.services.PersonServices;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController implements PersonControllerDocs {

    @Autowired
    private PersonServices service;

    @Override
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    public List<PersonDTO>findAll() {
        return service.findAll();

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

    @Override
    @DeleteMapping(value = "/{id}")


    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
