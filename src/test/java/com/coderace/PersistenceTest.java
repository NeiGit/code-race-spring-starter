package com.coderace;

import com.coderace.entity.Person;
import com.coderace.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersistenceTest {

    @Autowired
    PersonRepository repository;

    @Test
    void persistenceTest() {
/*        final Person person = new Person("Nei", 29);

        repository.save(person);

        final Optional<Person> persistedEntity = repository.findById(1);

        assertAll("Expected entity",
                () -> assertTrue(persistedEntity.isPresent()),
                () -> assertEquals("Nei", persistedEntity.get().getName()),
                () -> assertEquals(29, persistedEntity.get().getAge())
        );*/
    }

    @Test
    void unitIndependenceTest() {
        assertTrue(repository.findAll().isEmpty());
    }
}
