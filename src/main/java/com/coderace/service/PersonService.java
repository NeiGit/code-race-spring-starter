package com.coderace.service;

import com.coderace.dto.PersonBulkCreateRequestDTO;
import com.coderace.dto.PersonRequestDTO;
import com.coderace.dto.PersonResponseDTO;
import com.coderace.entity.Person;
import com.coderace.entity.enums.Country;
import com.coderace.repository.PersonRepository;
import com.coderace.service.log.LogService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository repository;
    private final LogService log;

    public PersonService(PersonRepository repository, LogService log) {
        this.repository = repository;
        this.log = log;

        this.log.setName("PersonService");
    }

    public PersonResponseDTO create(PersonRequestDTO requestDTO) {
        this.validate(requestDTO);

        final Country country = this.resolveCountry(requestDTO.getCountry());

        final Person person = new Person(requestDTO.getName(), requestDTO.getAge(), country);

        final Person persistedPerson = repository.save(person);

        this.log.info("Successfully created person with id " + persistedPerson.getId());

        return buildPersonResponseDto(persistedPerson);
    }

    public List<PersonResponseDTO> getAll(Integer minAge, String country) {
        final List<Person> allPersons = repository.findAll();
        final List<Person> filteredPersons = this.filter(allPersons, minAge, country);

        final List<PersonResponseDTO> responseDTOs = new ArrayList<>();

        for (Person person : filteredPersons) {
            final PersonResponseDTO responseDTO = buildPersonResponseDto(person);

            responseDTOs.add(responseDTO);
        }

        return responseDTOs;
    }

    public PersonResponseDTO findById(int id) {
        return this.repository.findById(id)
                .map(this::buildPersonResponseDto)
                .orElse(null);
    }

    private List<Person> filter(List<Person> persons, Integer minAge, String countryCode) {
        final Country countryFilter = countryCode == null ? null : Country.fromCode(countryCode);
        int ageFilter = minAge != null ? minAge : Integer.MIN_VALUE;

        return persons.stream()
                .filter(person -> {
                    final boolean countryCondition = countryFilter == null || person.getCountry() == countryFilter;
                    final boolean ageCondition = person.getAge() > ageFilter;

                    return countryCondition && ageCondition;
                }).collect(Collectors.toList());
    }

    private void validate(PersonRequestDTO requestDTO) {
        validateAge(requestDTO.getAge());

    }

    private void validateAge(Integer age) {
        if (age <= 0) {
            throw new RuntimeException("age must be greater than 0");
        }
    }

    private Country resolveCountry(String countryCode) {
        final Country country = Country.fromCode(countryCode);

        if (country == Country.UNDEFINED) {
            log.warning("Person without country");
        }

        return country;
    }

    private PersonResponseDTO buildPersonResponseDto(Person persistedPerson) {
        final LocalDate bornDate = LocalDateTime.now().minusYears(persistedPerson.getAge()).toLocalDate();

        final PersonResponseDTO responseDTO = new PersonResponseDTO();

        responseDTO
                .setName(persistedPerson.getName())
                .setAge(persistedPerson.getAge())
                .setCountry(persistedPerson.getCountry().getCode())
                .setBornDate(bornDate.toString());

        return responseDTO;
    }

    public List<PersonResponseDTO> bulkCreate(PersonBulkCreateRequestDTO request) {
        final int age = request.getAge();

        this.validateAge(age);

        final Person.Builder builder = new Person.Builder().setAge(age);

        return request.getPersons().stream().map(personRequest -> {
            final Person person = builder
                    .setName(personRequest.getName())
                    .setCountry(this.resolveCountry(personRequest.getCountry()))
                    .build();

            this.repository.save(person);

            return this.buildPersonResponseDto(person);
        }).collect(Collectors.toList());
    }
}
