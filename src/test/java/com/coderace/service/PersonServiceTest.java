package com.coderace.service;

import com.coderace.dto.PersonBulkCreateRequestDTO;
import com.coderace.dto.PersonRequestDTO;
import com.coderace.dto.PersonResponseDTO;
import com.coderace.entity.Person;
import com.coderace.entity.enums.Country;
import com.coderace.repository.PersonRepository;
import com.coderace.service.log.LogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    @DisplayName("getAll | no filters | ok")
    void getAllNoFiltersOk() {
        final Person person1 = new Person("name1", 1, Country.ARG);
        final Person person2 = new Person("name2", 2, Country.BRA);

        final List<Person> persons = Arrays.asList(person1, person2);

        when(repository.findAll()).thenReturn(persons);

        final List<PersonResponseDTO> result = service.getAll(null, null);

        assertEquals(persons.stream().map(this::toDto).collect(Collectors.toList()), result);
    }

    @Test
    @DisplayName("getAll | with filters | ok")
    void getAllWithFiltersOk() {
        final Person person1 = new Person("name1", 4, Country.ARG);
        final Person person2 = new Person("name2", 5, Country.USA);
        final Person person3 = new Person("name3", 2, Country.BRA);

        final List<Person> persons = Arrays.asList(person1, person2, person3);

        when(repository.findAll()).thenReturn(persons);

        final List<PersonResponseDTO> result = service.getAll(3, "arg");

        assertEquals(Collections.singletonList(this.toDto(person1)), result);
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

    @Test
    @DisplayName("bulkCreate | ok")
    void bulkCreateOk() {
        // given
        final PersonBulkCreateRequestDTO request = new PersonBulkCreateRequestDTO()
                .setAge(10)
                .setPersons(Arrays.asList(
                        new PersonBulkCreateRequestDTO.PersonBulkCreateDTO().setName("name").setCountry("bra"),
                        new PersonBulkCreateRequestDTO.PersonBulkCreateDTO().setName("name").setCountry("bra"),
                        new PersonBulkCreateRequestDTO.PersonBulkCreateDTO().setName("name").setCountry("bra"),
                        new PersonBulkCreateRequestDTO.PersonBulkCreateDTO().setName("name").setCountry("bra")
                        )
                );

        final Person person = new Person("name", 10, Country.BRA);

        when(repository.save(any(Person.class))).thenReturn(person);

        // when
        final List<PersonResponseDTO> response = service.bulkCreate(request);

        // then
        assertTrue(response.stream().allMatch(dto -> dto.equals(this.toDto(person))));
    }

    @Test
    @DisplayName("bulkCreate | when age is -1 | should throw RuntimeException")
    void bulkCreateInvalidAge() {
        // given
        final PersonBulkCreateRequestDTO request = new PersonBulkCreateRequestDTO()
                .setAge(0)
                .setPersons(new ArrayList<>());

        // when
        final RuntimeException exception = assertThrows(RuntimeException.class, () -> service.bulkCreate(request));

        // then
        assertEquals("age must be greater than 0", exception.getMessage());

        verify(repository, never()).save(any(Person.class));
    }

    private PersonResponseDTO toDto(Person person) {
        final LocalDate bornDate = LocalDateTime.now().minusYears(person.getAge()).toLocalDate();
        final PersonResponseDTO responseDTO = new PersonResponseDTO();

        responseDTO
                .setName(person.getName())
                .setAge(person.getAge())
                .setCountry(person.getCountry().getCode())
                .setBornDate(bornDate.toString());

        return responseDTO;
    }

}
