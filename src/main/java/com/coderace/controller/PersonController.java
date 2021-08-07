package com.coderace.controller;

import com.coderace.dto.PersonRequestDTO;
import com.coderace.dto.PersonResponseDTO;
import com.coderace.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService service;
    
    @PostMapping
    public PersonResponseDTO create(@RequestBody PersonRequestDTO requestDTO) {
        return service.create(requestDTO);
    }

    @GetMapping
    public List<PersonResponseDTO> getAll() {
        return service.getAll();
    }
}
