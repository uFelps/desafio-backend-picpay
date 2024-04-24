package com.picpay.simplificado.dtos;

import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.entities.enuns.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String fullname;
    private String document;
    private String email;
    private BigDecimal balance;
    private UserRole userRole;

    public UserDTO(User user){
        this.id = user.getId();
        this.fullname = user.getFullname();
        this.document = user.getDocument();
        this.email = user.getEmail();
        this.balance = user.getBalance();
        this.userRole = user.getUserRole();
    }
}
