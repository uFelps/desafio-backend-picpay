package com.picpay.simplificado.services;

import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.entities.enuns.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NotificationService service;
    private User userMock1;

    @BeforeEach
    void setUp(){
        userMock1 = new User(1L, "test", "111.111.111-11", "test@gmail.com", "test", BigDecimal.valueOf(1000), UserRole.COMMON);

    }

    @Test
    void sendNotificationShouldNotThrowExceptionWhenSucceeds(){
        Assertions.assertDoesNotThrow(() -> {
            service.sendNotification(userMock1, "message");
        });
    }

}