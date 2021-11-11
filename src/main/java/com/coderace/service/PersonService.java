package com.coderace.service;

import com.coderace.dto.PersonRequestDTO;
import com.coderace.dto.PersonResponseDTO;
import com.coderace.entity.Person;
import com.coderace.entity.enums.Country;
import com.coderace.repository.PersonRepository;
import com.coderace.service.log.LogService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<PersonResponseDTO> getAll() {
        final List<Person> persons = repository.findAll();

        final List<PersonResponseDTO> responseDTOs = new ArrayList<>();

        for (Person person : persons) {
            final PersonResponseDTO responseDTO = buildPersonResponseDto(person);

            responseDTOs.add(responseDTO);
        }

        return responseDTOs;
    }

    private void validate(PersonRequestDTO requestDTO) {
        if (requestDTO.getAge() <= 0) {
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
        final PersonResponseDTO responseDTO = new PersonResponseDTO();

        responseDTO
                .setName(persistedPerson.getName())
                .setAge(persistedPerson.getAge())
                .setCountry(persistedPerson.getCountry().getCode());

        return responseDTO;
    }

    public PersonResponseDTO findById(int id) {
        return this.repository.findById(id)
                .map(this::buildPersonResponseDto)
                .orElse(null);
    }
}
