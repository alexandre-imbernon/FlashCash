package com.alex.controller;

import com.alex.form.SignUpForm;
import com.alex.service.ProfileService;
import com.alex.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final ProfileService profileService;
    private final AuthService authService;

    public UserController(ProfileService profileService, AuthService authService) {
        this.profileService = profileService;
        this.authService = authService;
    }

    // Redirige directement la racine vers /profile
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/profile";
    }

    // Page profil utilisateur
    @GetMapping("/profile")
    public String profilePage(Model model) {
        return profileService.buildProfilePage(model);
    }

    // GET formulaire d'inscription
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        return authService.showSignUpForm(model).getViewName();
    }

    // POST inscription
    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("signUpForm") SignUpForm form) {
        return authService.registerUser(form);
    }

    // GET formulaire de connexion
    @GetMapping("/signin")
    public String showLoginForm() {
        return authService.showLoginForm();
    }
}
