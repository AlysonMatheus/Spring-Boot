package br.com.Alyson.integrationtests.controllers.withyaml;

import br.com.Alyson.config.TestConfigs;
import br.com.Alyson.integrationtests.controllers.withyaml.mapper.YAMLMapper;
import br.com.Alyson.integrationtests.dto.PersonDTO;
import br.com.Alyson.integrationtests.dto.wrappers.json.WrapperPersonDTO;
import br.com.Alyson.integrationtests.dto.wrappers.xml.PagedModelPerson;
import br.com.Alyson.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerYamlTest extends AbstractIntegrationTest {


    private static RequestSpecification specification;
    private static YAMLMapper objectMapper;
    private static PersonDTO person;

    @BeforeAll
   static void setUp() {
        objectMapper = new YAMLMapper();


        person = new PersonDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockPerson();


        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ALYSON)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var createdPerson = given().config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
             .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(person,objectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PersonDTO.class, objectMapper);

        person = createdPerson;

        assertNotNull(createdPerson.getId());
        Assertions.assertTrue(createdPerson.getId() > 0);


        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("F", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());
    }

@Test
    @Order(2)
    void UpdateTest() throws JsonProcessingException {
       person.setLastName("Benedict Torvalds");




        var createdPerson =  given().config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(person,objectMapper)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PersonDTO.class, objectMapper);


        person = createdPerson;

        assertNotNull(createdPerson.getId());
        Assertions.assertTrue(createdPerson.getId() > 0);


    assertEquals("Linus", createdPerson.getFirstName());
    assertEquals("Benedict Torvalds", createdPerson.getLastName());
    assertEquals("Helsinki - Finland", createdPerson.getAddress());
    assertEquals("F", createdPerson.getGender());
    assertTrue(createdPerson.getEnabled());


    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {


        var createdPerson =  given().config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id",person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PersonDTO.class, objectMapper);


        person = createdPerson;


        assertNotNull(createdPerson.getId());
        Assertions.assertTrue(createdPerson.getId() > 0);


        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("F", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());
    }
 @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {


        var createdPerson = given().config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                .spec(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id",person.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PersonDTO.class, objectMapper);


        person = createdPerson;


        assertNotNull(createdPerson.getId());
        Assertions.assertTrue(createdPerson.getId() > 0);


        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("F", createdPerson.getGender());
        assertFalse(createdPerson.getEnabled());
    }
 @Test
    @Order(5)
    void deleteTest() throws JsonProcessingException {


       given(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
               .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id",person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);



    }




    private void mockPerson() {
        person.setFirstName("Linus");
        person.setLastName("Benedict Torvalds");
        person.setAddress("Helsinki - Finland");
        person.setGender("F");
        person.setEnabled(true);
    }
    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {


        var response = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .queryParams("page",3,"size",12,"direction","asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PagedModelPerson.class, objectMapper);
        List<PersonDTO> people = response.getContent();

        PersonDTO personOne = people.get(0);
        person = personOne;


        assertNotNull(personOne.getId());
        Assertions.assertTrue(personOne.getId() > 0);


        assertEquals("Allin", personOne.getFirstName());
        assertEquals("Emmot", personOne.getLastName());
        assertEquals("7913 Lindbergh Way", personOne.getAddress());
        assertEquals("Male", personOne.getGender());
        assertFalse(personOne.getEnabled());

        PersonDTO personFour = people.get(4);



        assertNotNull(personOne.getId());
        Assertions.assertTrue(personOne.getId() > 0);


        assertEquals("Alonso", personFour.getFirstName());
        assertEquals("Luchelli", personFour.getLastName());
        assertEquals("9 Doe Crossing Avenue", personFour.getAddress());
        assertEquals("Male", personFour.getGender());
        assertFalse(personFour.getEnabled());
    }
    @Test
    @Order(7)
    void findByNameTest() throws JsonProcessingException {

//{{baseUrl}}/api/person/v1/findPeopleByName/and?page=0&size=12&direction=asc
        var content = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("firstNameYaml", "and")
                .queryParams("page", 0, "size", 12, "direction", "asc")
                .when()
                .get("findPeopleByName/{firstNameYaml}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PagedModelPerson.class,objectMapper);
//        WrapperPersonDTO wrapper = objectMapper.readValue(content, WrapperPersonDTO.class);
        List<PersonDTO> people = content.getContent();

        PersonDTO personOne = people.get(0);
        person = personOne;


        assertNotNull(personOne.getId());
        Assertions.assertTrue(personOne.getId() > 0);


        assertEquals("Alessandro", personOne.getFirstName());
        assertEquals("McFaul", personOne.getLastName());
        assertEquals("5 Lukken Plaza", personOne.getAddress());
        assertEquals("Male", personOne.getGender());
        assertTrue(personOne.getEnabled());

        PersonDTO personFour = people.get(4);


        assertNotNull(personOne.getId());
        Assertions.assertTrue(personOne.getId() > 0);


        assertEquals("Brandyn", personFour.getFirstName());
        assertEquals("Grasha", personFour.getLastName());
        assertEquals("96 Mosinee Parkway", personFour.getAddress());
        assertEquals("Male", personFour.getGender());
        assertTrue(personFour.getEnabled());

    }

    }
