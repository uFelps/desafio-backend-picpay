package com.picpay.simplificado.entities;

import com.picpay.simplificado.dtos.NewUserDTO;
import com.picpay.simplificado.entities.enuns.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "users")
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    @Column(unique = true)
    private String document;
    @Column(unique = true)
    private String email;
    private String password;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User(NewUserDTO dto) {
        this.fullname = dto.getFullname();
        this.document = dto.getDocument();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.balance = dto.getBalance();
        this.userRole = dto.getUserRole();
    }
}
