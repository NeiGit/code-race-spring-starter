package com.coderace;

import com.coderace.entity.Person;
import com.coderace.entity.enums.Country;
import com.coderace.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersistenceTest {

    @Autowired
    PersonRepository repository;

    @Test
    void persistenceTest() {
        final Person person = new Person("Nei", 29, Country.ARG);

        repository.save(person);

        final Person persistedEntity = repository.findById(1).orElseThrow(RuntimeException::new);

        assertAll("Expected entity",
                () -> assertEquals("Nei", persistedEntity.getName()),
                () -> assertEquals(29, persistedEntity.getAge()),
                () -> assertEquals(Country.ARG, persistedEntity.getCountry())
        );
    }

    @Test
    void unitIndependenceTest() {
        assertTrue(repository.findAll().isEmpty());
    }
}
