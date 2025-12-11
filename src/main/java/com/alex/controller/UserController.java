/**
 * Contrôleur MVC pour la gestion des utilisateurs.
 *
 * - Redirige la racine "/" vers "/profile".
 * - Affiche la page profil via ProfileService.
 * - Gère l'inscription (GET formulaire + POST traitement) via AuthService.
 * - Affiche la page de connexion via AuthService.
 *
 * Ce contrôleur est un "passe-plat" : il ne contient aucune logique métier,
 * il délègue entièrement aux services pour préparer les vues et gérer les actions.
 */

package com.alex.controller;
// Déclare le package : cette classe fait partie du module "controller" de ton application.

import com.alex.form.SignUpForm;
// Import du formulaire d'inscription, utilisé pour binder les données saisies par l'utilisateur.

import com.alex.service.ProfileService;
// Import du service qui gère la logique de la page profil.

import com.alex.service.AuthService;
// Import du service qui gère la logique d'authentification et d'inscription.

import org.springframework.stereotype.Controller;
// Annotation Spring qui déclare cette classe comme un contrôleur MVC.

import org.springframework.ui.Model;
// Permet de passer des données du backend vers la vue (template).

import org.springframework.web.bind.annotation.GetMapping;
// Annotation pour mapper une requête GET vers une méthode.

import org.springframework.web.bind.annotation.ModelAttribute;
// Annotation pour binder automatiquement les champs d'un formulaire à un objet Java.

import org.springframework.web.bind.annotation.PostMapping;
// Annotation pour mapper une requête POST vers une méthode.

@Controller
// Indique à Spring que cette classe est un contrôleur MVC.
public class UserController {

    private final ProfileService profileService;
    // Dépendance vers ProfileService : gère la logique de la page profil.

    private final AuthService authService;
    // Dépendance vers AuthService : gère la logique d'inscription et de connexion.

    public UserController(ProfileService profileService, AuthService authService) {
        // Constructeur avec injection des dépendances.
        this.profileService = profileService;
        this.authService = authService;
    }

    // Redirige directement la racine vers /profile
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/profile";
        // Quand on accède à "/", on est redirigé vers "/profile".
    }

    // Page profil utilisateur
    @GetMapping("/profile")
    public String profilePage(Model model) {
        return profileService.buildProfilePage(model);
        // Délègue à ProfileService la préparation de la page profil.
    }

    // GET formulaire d'inscription
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        return authService.showSignUpForm(model).getViewName();
        // Délègue à AuthService la préparation du formulaire d'inscription.
        // Retourne le nom de la vue "signup".
    }

    // POST inscription
    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("signUpForm") SignUpForm form) {
        return authService.registerUser(form);
        // Délègue à AuthService l'enregistrement du nouvel utilisateur.
        // Redirige ensuite vers "/signin".
    }

    // GET formulaire de connexion
    @GetMapping("/signin")
    public String showLoginForm() {
        return authService.showLoginForm();
        // Délègue à AuthService l'affichage de la page de connexion.
    }
}
