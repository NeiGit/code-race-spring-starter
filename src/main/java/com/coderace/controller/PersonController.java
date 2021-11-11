package com.coderace.controller;

import com.coderace.dto.PersonRequestDTO;
import com.coderace.dto.PersonResponseDTO;
import com.coderace.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService service;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody PersonRequestDTO requestDTO) {
        try {
            final PersonResponseDTO personResponseDTO = service.create(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(personResponseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable int id) {
        final PersonResponseDTO responseDTO = this.service.findById(id);

        if (responseDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public List<PersonResponseDTO> getAll(@RequestParam(required = false) Integer age,
                                          @RequestParam(required = false) String country) {
        return service.getAll(age, country);
    }
}
