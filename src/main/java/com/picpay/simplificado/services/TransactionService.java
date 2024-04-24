package com.picpay.simplificado.services;

import com.picpay.simplificado.dtos.NewTransactionDTO;
import com.picpay.simplificado.dtos.TransactionDTO;
import com.picpay.simplificado.entities.Transaction;
import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.entities.enuns.UserRole;
import com.picpay.simplificado.repositories.TransactionRepository;
import com.picpay.simplificado.services.exceptions.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private NotificationService notificationService;

    public TransactionDTO newTransaction(NewTransactionDTO dto){

        User sender = userService.findById(dto.getSender());
        User receiver = userService.findById(dto.getReceiver());

        validateTransaction(sender, dto.getAmount());

        if(!authorizationService.authorizeTransaction(sender, dto.getAmount())){
            throw new TransactionException("Unauthorized transfer");
        }

        Transaction newTransferencia = new Transaction(null, Instant.now(), dto.getAmount(), sender, receiver);
        sender.setBalance(sender.getBalance().subtract(dto.getAmount()));
        receiver.setBalance(receiver.getBalance().add(dto.getAmount()));

        userService.saveUser(sender);
        userService.saveUser(receiver);
        repository.save(newTransferencia);

        notificationService.sendNotification(sender, "Transfer sent successfully!");
        notificationService.sendNotification(receiver, "Transfer received successfully!");

        return new TransactionDTO(newTransferencia);
    }

    public void validateTransaction(User sender, BigDecimal amount) {
        if (sender.getUserRole() == UserRole.MERCHANT) {
            throw new TransactionException("Merchants can't do transactions");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new TransactionException("The sender does not have enough balance to transaction");
        }
    }
}
