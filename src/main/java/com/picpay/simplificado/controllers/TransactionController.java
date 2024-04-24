package com.picpay.simplificado.controllers;

import com.picpay.simplificado.dtos.NewTransactionDTO;
import com.picpay.simplificado.dtos.TransactionDTO;
import com.picpay.simplificado.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionDTO> newTrasaction(@RequestBody @Valid NewTransactionDTO dto){

        TransactionDTO newTransaction = service.newTransaction(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newTransaction.getId()).toUri();

        return ResponseEntity.created(uri).body(newTransaction);
    }
}
