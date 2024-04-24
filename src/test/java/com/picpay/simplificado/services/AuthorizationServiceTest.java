package com.picpay.simplificado.services;

import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.entities.enuns.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @InjectMocks
    private AuthorizationService service;

    private User userMock;

    @BeforeEach
    void setUp(){
        userMock = new User(1L, "test", "111.111.111", "test@gmail.com", "test", BigDecimal.valueOf(1000), UserRole.MERCHANT);
    }

    @Test
    void authorizeTransactionShouldReturnTrue(){
        boolean isAuthorized = service.authorizeTransaction(userMock, BigDecimal.valueOf(100));

        Assertions.assertTrue(isAuthorized);

    }

}