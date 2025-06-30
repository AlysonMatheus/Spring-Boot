package br.com.Alyson.integrationtests.controllers.withjson;

import br.com.Alyson.config.TestConfigs;
import br.com.Alyson.integrationtests.dto.PersonDTO;
import br.com.Alyson.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest extends AbstractIntegrationTest {


    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonDTO person;

    @BeforeAll
   static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonDTO();
    }

    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockPerson();


        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ALYSON)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        Assertions.assertTrue(createdPerson.getId() > 0);


        assertEquals("Alyson", createdPerson.getFirstName());
        assertEquals("Matheus", createdPerson.getLastName());
        assertEquals("Osvaldo Cruz - São Paulo - BRASIL", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());


    }
    @Test
    @Order(2)
    void createWithWronOrigin() throws JsonProcessingException {



        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_MATHEUS)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();



        assertEquals("Invalid CORS request", content);


    }

    @Test
    @Order(3)
    void findById() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id",person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        Assertions.assertTrue(createdPerson.getId() > 0);


        assertEquals("Alyson", createdPerson.getFirstName());
        assertEquals("Matheus", createdPerson.getLastName());
        assertEquals("Osvaldo Cruz - São Paulo - BRASIL", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }

    @Test
    @Order(4)
    void findByIdWithWronOrigin() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_MATHEUS)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id",person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();





        assertEquals("Invalid CORS request", content);
    }



    private void mockPerson() {
        person.setFirstName("Alyson");
        person.setLastName("Matheus");
        person.setAddress("Osvaldo Cruz - São Paulo - BRASIL");
        person.setGender("Male");
    }
}