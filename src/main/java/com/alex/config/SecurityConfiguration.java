/**
 * Configuration Spring Security de l'application.
 *
 * - Définit un PasswordEncoder basé sur BCrypt pour sécuriser les mots de passe.
 * - Configure les règles d'accès HTTP :
 *   - Autorise l'accès aux fichiers statiques et aux pages /signin et /signup.
 *   - Exige une authentification pour toutes les autres requêtes.
 * - Configure le formulaire de login :
 *   - Page personnalisée /signin.
 *   - Redirection vers /profile après succès.
 * - Configure la déconnexion (accessible à tous).
 * - Désactive CSRF (à réactiver si utilisation de formulaires classiques).
 *
 * Cette classe centralise la configuration de la sécurité et
 * remplace l'ancien mécanisme basé sur WebSecurityConfigurerAdapter.
 */

package com.alex.config;
// Déclare le package : cette classe fait partie du module "config" de ton application.

import org.springframework.context.annotation.Bean;
// Permet de déclarer des beans Spring (objets gérés par le conteneur).

import org.springframework.context.annotation.Configuration;
// Indique que cette classe est une classe de configuration Spring.

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// Classe utilisée pour configurer la sécurité HTTP (règles d'accès, authentification, etc.).

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// Implémentation de PasswordEncoder qui utilise l'algorithme BCrypt pour hacher les mots de passe.

import org.springframework.security.crypto.password.PasswordEncoder;
// Interface pour encoder et vérifier les mots de passe.

import org.springframework.security.web.SecurityFilterChain;
// Représente la chaîne de filtres de sécurité appliquée aux requêtes HTTP.

@Configuration
// Indique à Spring que cette classe contient des configurations de sécurité.
public class SecurityConfiguration {

    @Bean
    // Déclare un bean Spring pour l'encodage des mots de passe.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // Utilise BCrypt pour encoder les mots de passe (sécurisé et recommandé).
    }

    @Bean
    // Déclare un bean Spring pour la configuration de la chaîne de sécurité.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                                // Définition des règles d'autorisation :
                                // Autorise l'accès libre aux fichiers statiques et aux pages de connexion/inscription.
                                .requestMatchers("/css/**", "/images/**", "/signin", "/signup")
                                .permitAll()
                                .anyRequest().authenticated()
                        // Toute autre requête nécessite une authentification.
                )
                .formLogin(form -> form
                                .loginPage("/signin")
                                // Spécifie la page GET de connexion personnalisée.
                                .loginProcessingUrl("/signin")
                                // Spécifie l'URL POST utilisée pour traiter le formulaire de login.
                                .defaultSuccessUrl("/profile", true)
                                // Après une connexion réussie, redirige vers /profile.
                                .permitAll()
                        // Autorise tout le monde à accéder à la page de login.
                )
                .logout(logout -> logout.permitAll())
                // Permet à tout le monde de se déconnecter.
                .csrf(csrf -> csrf.disable());
        // Désactive la protection CSRF (utile si tu utilises des appels REST/JS).
        // ⚠️ Attention : à activer si tu utilises des formulaires classiques pour plus de sécurité.

        return http.build();
        // Construit et retourne la configuration de sécurité.
    }
}
