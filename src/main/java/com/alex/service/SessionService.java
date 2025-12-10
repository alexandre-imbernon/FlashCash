package com.alex.service;

import com.alex.model.User;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    public User sessionUser() {
        // TODO : récupérer l'utilisateur connecté
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        return user;
    }
}
