package com.picpay.simplificado.services.exceptions;

public class AuthorizationServiceException extends RuntimeException {
    public AuthorizationServiceException(String msg){
        super(msg);
    }
}
