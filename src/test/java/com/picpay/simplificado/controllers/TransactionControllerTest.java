package com.picpay.simplificado.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picpay.simplificado.dtos.NewTransactionDTO;
import com.picpay.simplificado.dtos.TransactionDTO;
import com.picpay.simplificado.services.TransactionService;
import com.picpay.simplificado.services.exceptions.TransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.Instant;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @Autowired
    private ObjectMapper objectMapper;

    private NewTransactionDTO inputTransactionDTO;
    private TransactionDTO outputTransactionDTO;


    @BeforeEach
    void setUp(){
        inputTransactionDTO = new NewTransactionDTO(BigDecimal.valueOf(500), 1L, 3L);
        outputTransactionDTO = new TransactionDTO(1L, Instant.now(), BigDecimal.valueOf(500), 1L, 3L);
    }

    @Test
    void newTransactionShouldReturn201CreatedWhenSucceeds() throws Exception {

        Mockito.when(service.newTransaction(Mockito.any())).thenReturn(outputTransactionDTO);

        String jsonBody = objectMapper.writeValueAsString(inputTransactionDTO);

        ResultActions result = mockMvc.perform(post("/transaction")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
    }

    @Test
    void newTransctionShouldReturnBadRequestWhenNotSucceeds() throws Exception {
        Mockito.doThrow(TransactionException.class).when(service).newTransaction(Mockito.any());

        inputTransactionDTO.setAmount(BigDecimal.valueOf(100000));
        String jsonBody = objectMapper.writeValueAsString(inputTransactionDTO);

        ResultActions result = mockMvc.perform(post("/transaction")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.error").exists());
    }

}