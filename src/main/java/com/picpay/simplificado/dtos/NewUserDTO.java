package com.picpay.simplificado.dtos;

import com.picpay.simplificado.entities.enuns.UserRole;
import com.picpay.simplificado.services.validations.NovoUserValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NovoUserValid
public class NewUserDTO {
    private Long id;
    @NotBlank
    private String fullname;
    @NotBlank
    private String document;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private BigDecimal balance;
    private UserRole userRole;
}
