package br.com.Alyson.Repository;

import br.com.Alyson.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.Alyson.model.Person;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)// Integra o Spring com o JUnit 5, permitindo injeção de dependências e uso do contexto Spring nos testes.

@DataJpaTest // Configura o teste para usar apenas componentes relacionados ao JPA (repositories, entidades).
// Utiliza, por padrão, um banco de dados em memória (ex: H2), a menos que seja sobrescrito.

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Evita substituir o banco de dados de testes pelo banco em memória.
// Permite testar com o banco de dados real configurado na aplicação (útil para testes de integração reais).

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)// Evita substituir o banco de dados de testes pelo banco em memória.
// Permite testar com o banco de dados real configurado na aplicação (útil para testes de integração reais).

class PersonRepositoryTest extends AbstractIntegrationTest {
@Autowired
PersonRepository repository; // Injeta o PersonRepository para que os métodos de acesso ao banco possam ser testados.

    private static Person person;
    @BeforeAll
   static void setUp() {
        person = new Person();// Inicializa um objeto Person antes da execução de todos os testes.
// Será usado como mock ou placeholder para armazenar dados ao longo dos testes.

    }

    @Test
    @Order(1)
    void findPeopleByName() {
        // Realiza uma busca paginada por nome ("elso") ordenada por 'firstName' (ASC).
// Armazena a primeira pessoa retornada na variável 'person'.
// Verifica se os dados retornados da consulta estão corretos usando assertions.


        Pageable pageable = PageRequest.of(
                0,
                12,
                Sort.by(Sort.Direction.ASC, "firstName"));

        person = repository.findPeopleByName("elso",pageable).getContent().get(0);
        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Nelson",person.getFirstName());
        assertEquals("Mandela",person.getLastName());
        assertEquals("São Paulo- Brasil - 2025",person.getAddress());
        assertTrue(person.getEnabled());
        assertEquals("Male",person.getGender());
    }

    @Test
    @Order(2)
    void disablePerson() {
// Desabilita (setEnabled = false) a pessoa usando o ID salvo no teste anterior.
// Recupera a pessoa do banco novamente e verifica se o campo 'enabled' foi atualizado corretamente.

        Long id = person.getId();
        repository.disablePerson(id);

        var  result = repository.findById(id);
        person = result.get();

        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Nelson",person.getFirstName());
        assertEquals("Mandela",person.getLastName());
        assertEquals("São Paulo- Brasil - 2025",person.getAddress());
        assertEquals("Male",person.getGender());
        assertFalse(person.getEnabled());


    }
}