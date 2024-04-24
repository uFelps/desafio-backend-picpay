package com.picpay.simplificado.repositories;

import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.entities.enuns.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private User userTest;
    private Long countItensRepository;
    private Long existingId;
    private Long nonExistingId;
    private String existingDocument;
    private String existingEmail;

    @BeforeEach
    void setUp(){
        userTest = new User(null, "test", "111.111.111.11", "test", "test", BigDecimal.ZERO, UserRole.COMMON);
        countItensRepository = repository.count();
        existingId = 1L;
        nonExistingId = 100L;
        existingDocument = "542.733.082-36";
        existingEmail = "felipe@gmail.com";
    }

    @Test
    void saveShouldCreateNewUserWhenIdIsNull(){

        User result = repository.save(userTest);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getPassword());
        Assertions.assertEquals(countItensRepository + 1, result.getId());

    }

    @Test
    void findByDocumentShouldReturnObjectWhenIdExists(){

        User result = repository.findByDocument(existingDocument);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingDocument, result.getDocument());
    }

    @Test
    void findByDocumentReturnNullWhenIdNotExists(){

        User result = repository.findByDocument("");

        Assertions.assertNull(result);
    }

    @Test
    void findByEmailShouldReturnObjectWhenIdExists(){

        User result = repository.findByEmail(existingEmail);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingEmail, result.getEmail());
    }

    @Test
    void findByEmailShouldReturnNullWhenIdNotExists(){

        User result = repository.findByEmail("");

        Assertions.assertNull(result);
    }

    @Test
    void findByIdShouldReturnOptionalWithUserWhenIdExists(){

        Optional<User> result = repository.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(existingId, result.get().getId());

    }

    @Test
    void findByIdShouldReturnOptionalEmptyWhenIdNotExists() {

        Optional<User> result = repository.findById(nonExistingId);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }


}