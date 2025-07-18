package br.com.Alyson.integrationtests.controllers.withxml;

import br.com.Alyson.config.TestConfigs;
import br.com.Alyson.integrationtests.dto.PersonDTO;
import br.com.Alyson.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerXmlTest extends AbstractIntegrationTest {


    private static RequestSpecification specification;
    private static XmlMapper objectMapper;
    private static PersonDTO person;

    @BeforeAll
   static void setUp() {
        objectMapper = new XmlMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

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

        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        Assertions.assertTrue(createdPerson.getId() > 0);


        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Stallman", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("F", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());
    }

@Test
    @Order(2)
    void UpdateTest() throws JsonProcessingException {
       person.setLastName("Benedict Torvalds");




        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .body(person)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
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


        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("id",person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
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


        var content = given(specification)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("id",person.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
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
                .contentType(MediaType.APPLICATION_XML_VALUE)
               .accept(MediaType.APPLICATION_XML_VALUE)
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


        var content = given(specification)
                .accept(MediaType.APPLICATION_XML_VALUE)

                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();
        List<PersonDTO> people = objectMapper.readValue(content, new TypeReference<List<PersonDTO>>() {});

        PersonDTO personOne = people.get(0);
        person = personOne;


        assertNotNull(personOne.getId());
        Assertions.assertTrue(personOne.getId() > 0);


        assertEquals("Alyson", personOne.getFirstName());
        assertEquals("Senna", personOne.getLastName());
        assertEquals("Osvaldo Cruz-Brsil", personOne.getAddress());
        assertEquals("F", personOne.getGender());
        assertTrue(personOne.getEnabled());

        PersonDTO personFour = people.get(4);



        assertNotNull(personOne.getId());
        Assertions.assertTrue(personOne.getId() > 0);


        assertEquals("Cassio", personFour.getFirstName());
        assertEquals("Ramos", personFour.getLastName());
        assertEquals("São Paulo- Brasil - 2025", personFour.getAddress());
        assertEquals("F", personFour.getGender());
        assertTrue(personFour.getEnabled());
    }

    }
