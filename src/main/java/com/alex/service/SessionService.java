package com.alex.service;

import com.alex.model.User;
import com.alex.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final UserRepository userRepository;

    public SessionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retourne l'utilisateur actuellement connecté (ou null si personne).
     */
    public User sessionUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof UserDetails)) {
            return null;
        }

        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        return userRepository.findByEmail(email).orElse(null);
    }
}
