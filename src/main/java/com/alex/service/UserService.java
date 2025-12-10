package com.alex.service;

import com.alex.form.SignUpForm;
import com.alex.model.User;
import com.alex.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registration(SignUpForm form) {

        // 1. Vérif email déjà utilisé
        userRepository.findByEmail(form.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email déjà utilisé");
        });

        // 2. Vérif mots de passe identiques
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new RuntimeException("Les mots de passe ne correspondent pas");
        }

        // 3. Mapping
        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        // 4. Enregistrement
        userRepository.save(user);
    }
}
