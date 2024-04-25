package com.picpay.simplificado.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.picpay.simplificado.dtos.NewUserDTO;
import com.picpay.simplificado.dtos.UserDTO;
import com.picpay.simplificado.entities.enuns.UserRole;
import com.picpay.simplificado.services.UserService;
import com.picpay.simplificado.services.exceptions.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    private NewUserDTO insertDTO;
    private UserDTO outputDTO;

    @BeforeEach
    void setUp(){
        insertDTO = new NewUserDTO(null, "Roger Waters", "555.555.555-55", "roger1@gmail.com", "roger123", BigDecimal.valueOf(1000), UserRole.COMMON);
        outputDTO = new UserDTO(1L, "Roger Waters", "555.555.555-55", "roger1@gmail.com", BigDecimal.valueOf(1000), UserRole.COMMON);
    }

    @Test
    void saveNewUserShouldReturn201Created() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(insertDTO);

        Mockito.when(service.insert(Mockito.any())).thenReturn(outputDTO);
        Mockito.doThrow(DataNotFoundException.class).when(service).findByDocument(Mockito.any());
        Mockito.doThrow(DataNotFoundException.class).when(service).findByEmail(Mockito.any());

        ResultActions result = mockMvc.perform(post("/users")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.fullname").exists());
    }

}