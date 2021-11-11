package com.coderace.service;

import com.coderace.dto.PersonRequestDTO;
import com.coderace.dto.PersonResponseDTO;
import com.coderace.entity.Person;
import com.coderace.entity.enums.Country;
import com.coderace.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PersonService test | Unit")
public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @Mock
    private LogService log;

    @InjectMocks
    private PersonService service;

    @Test
    @DisplayName("create | ok")
    void createOk() {
        // given
        final String name = "name";
        final int age = 18;
        final Country country = Country.ARG;

        final PersonRequestDTO request = new PersonRequestDTO(name, age, country.getCode());

        final Person person = new Person(name, age, country);

        when(repository.save(any(Person.class))).thenReturn(person);

        // when
        final PersonResponseDTO response = service.create(request);

        verify(log, times(1)).info(anyString());

        // then
        assertAll("Expected result",
                () -> assertEquals(name, response.getName()),
                () -> assertEquals(age, response.getAge()),
                () -> assertEquals(country.getCode(), response.getCountry())
        );
    }

    @Test
    @DisplayName("create | when age is -1 | should throw RuntimeException")
    void createInvalidAge() {
        // given
        final String name = "name";
        final int age = -1;
        final Country country = Country.ARG;

        final PersonRequestDTO request = new PersonRequestDTO(name, age, country.getCode());

        // when
        final RuntimeException exception = assertThrows(RuntimeException.class, () -> service.create(request));

        // then
        assertEquals("age must be greater than 0", exception.getMessage());

        verify(repository, never()).save(any(Person.class));
    }

    @Test
    @DisplayName("findById | ok")
    void findByIdOk() {
        // given
        final int id = 1;

        final String name = "name";
        final int age = 18;
        final Country country = Country.ARG;

        final Person person = new Person(name, age, country);

        when(repository.findById(id)).thenReturn(Optional.of(person));

        // when
        final PersonResponseDTO response = service.findById(id);

        // then
        assertAll("Expected result",
                () -> assertEquals(name, response.getName()),
                () -> assertEquals(age, response.getAge()),
                () -> assertEquals(country.getCode(), response.getCountry())
        );
    }

    @Test
    @DisplayName("findById | not found | should return null")
    void findByIdNotFound() {
        // given
        final int id = 1;

        when(repository.findById(id)).thenReturn(Optional.empty());

        // when
        final PersonResponseDTO response = service.findById(id);

        // then
        assertNull(response);
    }
}
