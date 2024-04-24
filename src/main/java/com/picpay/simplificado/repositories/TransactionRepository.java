package com.picpay.simplificado.repositories;

import com.picpay.simplificado.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
