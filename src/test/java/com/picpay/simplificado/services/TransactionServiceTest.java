package com.picpay.simplificado.services;

import com.picpay.simplificado.dtos.NewTransactionDTO;
import com.picpay.simplificado.dtos.TransactionDTO;
import com.picpay.simplificado.entities.Transaction;
import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.entities.enuns.UserRole;
import com.picpay.simplificado.repositories.TransactionRepository;
import com.picpay.simplificado.services.exceptions.TransactionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private UserService userService;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private NotificationService notificationService;


    @InjectMocks
    private TransactionService service;

    private User userMock;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        userMock = new User(1L, "test", "111.111.111", "test@gmail.com", "test", BigDecimal.valueOf(1000), UserRole.COMMON);


    }

    @Test
    void validateTransactionShouldReturnTrueWhenTransferIsValid(){
        Assertions.assertDoesNotThrow(() -> {
            service.validateTransaction(userMock, BigDecimal.valueOf(500));
        });
    }

    @Test
    void validateTransactionShouldThrowExceptionWhenTransferIsNotValid(){
        Assertions.assertThrows(TransactionException.class, () -> {
           service.validateTransaction(userMock, BigDecimal.valueOf(1500));
        });
    }

    @Test
    void validateTransactionShouldThrowExceptionWhenTransferIsNotValid2(){
        Assertions.assertThrows(TransactionException.class, () -> {
            userMock.setUserRole(UserRole.MERCHANT);
            service.validateTransaction(userMock, BigDecimal.valueOf(500));
        });
    }

    @Test
    void newTransactionShouldReturnDTOWhenSucceeds(){

        User sender = new User(1L, "João", "111.111.111-31", "joão@gmail.com", "joão123", BigDecimal.valueOf(1000), UserRole.COMMON);
        User receiver = new User(2L, "Amazon", "111.111.211-31", "amazon@gmail.com", "amazon123", BigDecimal.valueOf(10000), UserRole.MERCHANT);
        Mockito.when(userService.findById(1L)).thenReturn(sender);
        Mockito.when(userService.findById(2L)).thenReturn(receiver);
        Mockito.when(authorizationService.authorizeTransaction(Mockito.any(), Mockito.any())).thenReturn(true);

        TransactionDTO result = service.newTransaction(new NewTransactionDTO(BigDecimal.valueOf(100), sender.getId(), receiver.getId()));


        Mockito.verify(userService, Mockito.times(2)).findById(Mockito.any());
        Mockito.verify(userService, Mockito.times(1)).saveUser(sender);
        Mockito.verify(userService, Mockito.times(1)).saveUser(receiver);
        Mockito.verify(repository, Mockito.times(1)).save((Transaction) Mockito.any());
        Mockito.verify(notificationService, Mockito.times(2)).sendNotification(Mockito.any(), Mockito.any());
    }

    @Test
    void newTransactionShouldThrowExceptionWhenNotAuthorized(){

        User sender = new User(1L, "João", "111.111.111-31", "joão@gmail.com", "joão123", BigDecimal.valueOf(1000), UserRole.COMMON);
        User receiver = new User(2L, "Amazon", "111.111.211-31", "amazon@gmail.com", "amazon123", BigDecimal.valueOf(10000), UserRole.MERCHANT);
        Mockito.when(userService.findById(1L)).thenReturn(sender);
        Mockito.when(userService.findById(2L)).thenReturn(receiver);
        Mockito.when(authorizationService.authorizeTransaction(Mockito.any(), Mockito.any())).thenReturn(false);

        Assertions.assertThrows(TransactionException.class, () -> {
            TransactionDTO result = service.newTransaction(new NewTransactionDTO(BigDecimal.valueOf(100), sender.getId(), receiver.getId()));
        });
    }
}