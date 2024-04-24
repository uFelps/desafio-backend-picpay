package com.picpay.simplificado.services.exceptions;

public class TransactionException extends RuntimeException {
    public TransactionException(String msg){
        super(msg);
    }
}
