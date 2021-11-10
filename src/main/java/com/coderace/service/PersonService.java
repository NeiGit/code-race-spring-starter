package com.coderace.service;

import com.coderace.dto.PersonRequestDTO;
import com.coderace.dto.PersonResponseDTO;
import com.coderace.entity.Person;
import com.coderace.repository.PersonRepository;
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
        final Person person = new Person(requestDTO.getName(), requestDTO.getAge());

        final Person persistedPerson = repository.save(person);

        this.log.info("Created person with id " + persistedPerson.getId());

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

    private PersonResponseDTO buildPersonResponseDto(Person persistedPerson) {
        final PersonResponseDTO responseDTO = new PersonResponseDTO();

        responseDTO
                .setName(persistedPerson.getName())
                .setAge(persistedPerson.getAge());

        return responseDTO;
    }
}
