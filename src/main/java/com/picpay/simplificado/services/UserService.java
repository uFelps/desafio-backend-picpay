package com.picpay.simplificado.services;

import com.picpay.simplificado.dtos.NewUserDTO;
import com.picpay.simplificado.dtos.UserDTO;
import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.repositories.UserRepository;
import com.picpay.simplificado.services.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserDTO insert(NewUserDTO dto){
        User user = new User(dto);
        user = repository.save(user);
        return new UserDTO(user);
    }

    public User findByDocument(String document) {
        User user =  repository.findByDocument(document);
        if(user == null){
            throw new DataNotFoundException("Document not found: " + document);
        }

        return user;
    }

    public User findByEmail(String email) {
        User user = repository.findByEmail(email);
        if(user == null){
            throw new DataNotFoundException("Email not found: " + email);
        }

        return user;
    }

    public User findById(Long id) {
       Optional<User> user =  repository.findById(id);

       if(user.isEmpty()){
           throw new DataNotFoundException("Id not found: " + id);
       }

       return user.get();
    }

    public void saveUser(User user) {
        repository.save(user);
    }
}
