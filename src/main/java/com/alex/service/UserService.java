package com.alex.service;

import com.alex.form.SignUpForm;
import com.alex.model.User;
import com.alex.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- Inscription ---
    public void registration(SignUpForm form) {
        // Vérif email déjà utilisé
        userRepository.findByEmail(form.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email déjà utilisé");
        });

        // Vérif mots de passe identiques
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new RuntimeException("Les mots de passe ne correspondent pas");
        }

        // Mapping
        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        // Enregistrement
        userRepository.save(user);
    }

    // --- Recherche utilisateur par email ---
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // --- Vérification mot de passe ---
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // --- Implémentation UserDetailsService pour Spring Security ---
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + email));

        // Conversion de ton User en UserDetails attendu par Spring Security
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // déjà encodé avec BCrypt
                .roles("USER") // tu peux gérer des rôles si besoin
                .build();
    }
}
