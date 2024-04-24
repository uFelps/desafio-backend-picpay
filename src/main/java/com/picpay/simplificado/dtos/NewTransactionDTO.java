package com.picpay.simplificado.dtos;

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
public class NewTransactionDTO {
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Long sender;
    @NotNull
    private Long receiver;
}
