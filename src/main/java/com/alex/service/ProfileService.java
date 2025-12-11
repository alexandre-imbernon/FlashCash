package com.alex.service;

import com.alex.model.User;
import com.alex.model.Transfer;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class ProfileService {

    private final SessionService sessionService;
    private final TransferService transferService;

    public ProfileService(SessionService sessionService, TransferService transferService) {
        this.sessionService = sessionService;
        this.transferService = transferService;
    }

    public String buildProfilePage(Model model) {
        User user = sessionService.sessionUser();
        if (user == null) {
            return "redirect:/signin"; // redirection si pas connecté
        }

        model.addAttribute("user", user);

        // Tu peux décider si tu veux toutes les transactions ou seulement celles de l’utilisateur
        List<Transfer> transactions = transferService.findTransactionsByUser(user);
        model.addAttribute("transfers", transactions);

        return "profile"; // vue unique
    }
}

