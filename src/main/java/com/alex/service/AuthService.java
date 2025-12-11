/**
 * Service d'authentification et d'inscription des utilisateurs.
 *
 * - Prépare le formulaire d'inscription (showSignUpForm).
 * - Enregistre un nouvel utilisateur via UserService (registerUser).
 * - Affiche la page de connexion (showLoginForm).
 *
 * Ce service permet de délocaliser la logique hors du contrôleur,
 * afin que celui-ci se contente de déléguer.
 */

package com.alex.service;
// Déclare le package : ici, la classe fait partie du module "service" de ton application.

import com.alex.form.SignUpForm;
// Import du formulaire d'inscription, utilisé pour binder les données saisies par l'utilisateur.

import org.springframework.stereotype.Service;
// Annotation Spring pour déclarer cette classe comme un "Service" (composant métier).

import org.springframework.ui.Model;
// Permet de passer des données du backend vers la vue (template).

import org.springframework.web.servlet.ModelAndView;
// Objet qui combine modèle + vue, utilisé pour retourner une page avec ses données.

@Service
// Indique à Spring que cette classe est un bean de service, instancié automatiquement.
public class AuthService {

    private final UserService userService;
    // Dépendance vers UserService : c'est lui qui gère la logique métier des utilisateurs.

    public AuthService(UserService userService) {
        // Injection par constructeur : Spring fournit automatiquement une instance de UserService.
        this.userService = userService;
    }

    public ModelAndView showSignUpForm(Model model) {
        // Prépare la page d'inscription.
        model.addAttribute("signUpForm", new SignUpForm());
        // Ajoute un objet vide "SignUpForm" au modèle, pour que la vue puisse binder les champs du formulaire.
        return new ModelAndView("signup");
        // Retourne la vue "signup" (template HTML), avec le modèle associé.
    }

    public String registerUser(SignUpForm form) {
        // Traite le formulaire d'inscription soumis par l'utilisateur.
        userService.registration(form);
        // Délègue à UserService la création du nouvel utilisateur.
        return "redirect:/signin";
        // Après inscription, redirige vers la page de connexion.
    }

    public String showLoginForm() {
        // Affiche simplement la page de connexion.
        return "signin";
        // Retourne le nom de la vue "signin" (template HTML).
    }
}