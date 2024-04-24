package com.picpay.simplificado.services.validations;

import com.picpay.simplificado.controllers.exceptions.FieldMessage;
import com.picpay.simplificado.dtos.NewUserDTO;
import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.services.UserService;
import com.picpay.simplificado.services.exceptions.DataNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class NovoUserValidator implements ConstraintValidator<NovoUserValid, NewUserDTO> {

    @Autowired
    private UserService service;

    @Override
    public void initialize(NovoUserValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(NewUserDTO dto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();


        // Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
        try{
             service.findByDocument(dto.getDocument());
             list.add(new FieldMessage("DOCUMENT", "This CPF/CNPJ has already been registered!"));
        }
        catch (DataNotFoundException e){

        }

        try {
            service.findByEmail(dto.getEmail());
            list.add(new FieldMessage("EMAIL", "This email has already been registered!"));
        }
        catch (DataNotFoundException e){

        }


        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
