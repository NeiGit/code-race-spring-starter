package com.coderace.controller;

import com.coderace.dto.PersonRequestDTO;
import com.coderace.dto.PersonResponseDTO;
import com.coderace.service.LogService;
import com.coderace.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("PersonController test | Unit")
@WebMvcTest
public class PersonControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PersonService service;

    @MockBean
    LogService log;


    @Test
    @DisplayName("create | ok")
    void createOk() throws Exception {
        // setup - given
        final PersonRequestDTO request = new PersonRequestDTO().setName("name").setAge(1).setCountry("arg");
        final PersonResponseDTO expectedResponse = new PersonResponseDTO().setName("name").setAge(1).setCountry("arg");

        when(service.create(request)).thenReturn(expectedResponse);

        // perform - when
        final MvcResult result = mvc.perform(post("/person/create")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andReturn();

        final PersonResponseDTO actualResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), PersonResponseDTO.class);

        // assert - then
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("create | when service throws RuntimeException | bad request")
    void createBadRequest() throws Exception {
        // setup - given
        final PersonRequestDTO request = new PersonRequestDTO().setName("name").setAge(1).setCountry("arg");

        final String expectedMessage = "test-message";

        when(service.create(request)).thenThrow(new RuntimeException(expectedMessage));

        // perform - when
        final MvcResult result = mvc.perform(post("/person/create")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andReturn();

        final String actualMessage = result.getResponse().getContentAsString();

        // assert - then
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("findById | ok")
    void findByIdOk() throws Exception {
        // setup - given
        final int id = 1;
        final PersonResponseDTO expectedResponse = new PersonResponseDTO().setName("name").setAge(1).setCountry("arg");

        when(service.findById(id)).thenReturn(expectedResponse);

        // perform - when
        final MvcResult result = mvc.perform(get("/person/" + id)).andReturn();

        final PersonResponseDTO actualResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), PersonResponseDTO.class);

        // assert - then
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("findById | not found")
    void findByIdNotFound() throws Exception {
        // setup - given
        final int id = 1;

        when(service.findById(id)).thenReturn(null);

        // perform - when
        final MvcResult result = mvc.perform(get("/person/" + id)).andReturn();

        // assert - then
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }
}
