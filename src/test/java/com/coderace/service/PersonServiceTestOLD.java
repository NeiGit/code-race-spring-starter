package com.coderace.service;

import com.coderace.dto.PersonRequestDTO;
import com.coderace.dto.PersonResponseDTO;
import com.coderace.entity.Person;
import com.coderace.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTestOLD {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    @Test
    @DisplayName("create | ok")
    void createOk() {
        final String name = "name";
        final int age = 20;
        final Person person = mock(Person.class);

        when(person.getName()).thenReturn(name);
        when(person.getAge()).thenReturn(age);

        when(repository.save(any(Person.class))).thenReturn(person);

        final PersonResponseDTO responseDto = service.create(mock(PersonRequestDTO.class));

        assertAll("Expected response",
                () -> assertEquals(name, responseDto.getName()),
                () -> assertEquals(age, responseDto.getAge()));
    }

}