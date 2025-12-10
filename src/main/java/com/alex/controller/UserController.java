package com.alex.controller;

import com.alex.model.Transfer;
import com.alex.model.User;
import com.alex.service.LinkService;
import com.alex.service.UserService;
import com.alex.service.TransferService;
import com.alex.service.SessionService;
import com.alex.form.SignUpForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    private final LinkService linkService;
    private final UserService userService;
    private final TransferService transferService;
    private final SessionService sessionService;

    public UserController(LinkService linkService,
                          UserService userService,
                          TransferService transferService,
                          SessionService sessionService) {

        this.linkService = linkService;
        this.userService = userService;
        this.transferService = transferService;
        this.sessionService = sessionService;
    }

    // --- Page d'accueil, accessible après login ---
    @GetMapping("/")
    public ModelAndView home(Model model) {
        User user = sessionService.sessionUser();
        if (user == null) {
            return new ModelAndView("redirect:/signin");
        }

        model.addAttribute("user", user);

        List<Transfer> transactions = transferService.findTransactions();
        model.addAttribute("transfers", transactions);

        return new ModelAndView("index"); // renvoie index.html
    }

    // --- GET formulaire d'inscription ---
    @GetMapping("/signup")
    public ModelAndView showSignUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return new ModelAndView("signup");  // signup.html
    }

    // --- GET formulaire de connexion ---
    @GetMapping("/signin")
    public String showLoginForm() {
        return "signin"; // signin.html
    }

    // --- POST inscription ---
    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("signUpForm") SignUpForm form) {
        userService.registration(form);
        return "redirect:/signin"; // redirige vers login après inscription
    }
}
