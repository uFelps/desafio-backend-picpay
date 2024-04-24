package com.picpay.simplificado.repositories;

import com.picpay.simplificado.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByDocument(String documento);

    User findByEmail(String email);
}
