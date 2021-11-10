package com.coderace.controller;

import com.coderace.dto.PersonRequestDTO;
import com.coderace.dto.PersonResponseDTO;
import com.coderace.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONValue;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PersonControllerTestOLD {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PersonService service;

    @Test
    @DisplayName("create | ok")
    void createOk() throws Exception {
        final PersonRequestDTO request = new PersonRequestDTO().setName("name").setAge(1);
        final PersonResponseDTO expectedResponse = new PersonResponseDTO().setName("name").setAge(1);

        when(service.create(request)).thenReturn(expectedResponse);

        // mockeo la interacción
        final MvcResult result = mvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON).content(JSONValue.toJSONString(request))) // body
                .andDo(print())
                .andExpect(status().is(HttpStatus.SC_CREATED)) // status assert
                .andReturn();

        final PersonResponseDTO actualResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), PersonResponseDTO.class);

        assertEquals(expectedResponse, actualResponse);
    }
}