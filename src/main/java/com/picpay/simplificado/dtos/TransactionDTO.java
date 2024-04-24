package com.picpay.simplificado.dtos;

import com.picpay.simplificado.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionDTO {

    private Long id;
    private Instant timestamp;
    private BigDecimal amount;
    private Long sender;
    private Long receiver;

    public TransactionDTO(Transaction transferencia){
        this.id = transferencia.getId();
        this.timestamp = transferencia.getTimestamp();
        this.amount = transferencia.getAmount();
        this.sender = transferencia.getSender().getId();
        this.receiver = transferencia.getReceiver().getId();
    }
}
