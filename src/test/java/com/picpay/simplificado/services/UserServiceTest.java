package com.picpay.simplificado.services;

import com.picpay.simplificado.dtos.NewUserDTO;
import com.picpay.simplificado.dtos.UserDTO;
import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.entities.enuns.UserRole;
import com.picpay.simplificado.repositories.UserRepository;
import com.picpay.simplificado.services.exceptions.DataNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;


    @InjectMocks
    private UserService service;
    private NewUserDTO inputDto;
    private User userMock1;
    private Long countTotalUsers;
    private Long existingId;
    private Long nonExistingId;
    private String existingDocument;
    private String nonExistingDocument;
    private String existingEmail;
    private String nonExistingEmail;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inputDto = new NewUserDTO(null, "test", "111.111.111-11", "test@gmail.com", "test", BigDecimal.valueOf(1000), UserRole.COMMON);
        userMock1 = new User(1L, "test", "111.111.111-11", "test@gmail.com", "test", BigDecimal.valueOf(1000), UserRole.COMMON);
        countTotalUsers = repository.count();

        existingId = 1L;
        nonExistingId = 100L;
        existingDocument = "111.111.111-11";
        nonExistingDocument = "999.999.999-99";
        existingEmail = "test@gmail.com";
        nonExistingDocument = "abcdef@gmail.com";
    }

    @Test
    void saveNewUserShouldSaveNewUser() {

        User userOutput = new User(inputDto);
        userOutput.setId(countTotalUsers + 1);

        Mockito.when(repository.save((User) Mockito.any())).thenReturn(userOutput);

        UserDTO result = service.insert(inputDto);


        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(countTotalUsers + 1, result.getId());
        Assertions.assertEquals(inputDto.getFullname(), result.getFullname());
    }

    @Test
    void findByDocumentShouldReturnUserWhenDocumentExists(){

        Mockito.when(repository.findByDocument(existingDocument)).thenReturn(userMock1);

        User result = service.findByDocument(existingDocument);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingDocument, result.getDocument());
    }

    @Test
    void findByDocumentShoudThrowExceptionWhenDocumentNotExists(){
        Mockito.when(repository.findByDocument(nonExistingDocument)).thenReturn(null);

        Assertions.assertThrows(DataNotFoundException.class, () -> {
            User result = service.findByDocument(nonExistingDocument);
        });
    }

    @Test
    void findByEmailShouldReturnUserWhenEmailExists(){

        Mockito.when(repository.findByEmail(existingEmail)).thenReturn(userMock1);

        User result = service.findByEmail(existingEmail);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingEmail, result.getEmail());
    }

    @Test
    void findByEmailShoudThrowExceptionWhenEmailNotExists(){
        Mockito.when(repository.findByEmail(nonExistingEmail)).thenReturn(null);

        Assertions.assertThrows(DataNotFoundException.class, () -> {
            User result = service.findByEmail(nonExistingEmail);
        });
    }

    @Test
    void findByIdShouldReturnUserWhenIdExists(){

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(userMock1));

        User result = service.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingId, result.getId());
    }

    @Test
    void findByIdShoudThrowExceptionWhenIdNotExists(){
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Assertions.assertThrows(DataNotFoundException.class, () -> {
            User result = service.findById(nonExistingId);
        });
    }



}