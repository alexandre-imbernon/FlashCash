/**
 * Configuration Spring MVC pour les contrôleurs de vue simples.
 *
 * - Déclare un mapping direct entre l'URL "/signup" et la vue "signup".
 * - Évite de créer un contrôleur dédié lorsqu'il n'y a pas de logique métier.
 *
 * Cette classe permet de simplifier la navigation en mappant des pages statiques
 * ou des formulaires directement à leurs vues.
 */

package com.alex.config;
// Déclare le package : cette classe fait partie du module "config" de ton application.

import org.springframework.context.annotation.Configuration;
// Import de l’annotation @Configuration : indique que cette classe contient des configurations Spring.

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
// Classe utilisée pour enregistrer des contrôleurs de vue simples (sans logique métier).

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// Interface permettant de personnaliser la configuration Spring MVC.

@Configuration
// Indique à Spring que cette classe est une classe de configuration (équivalent à un fichier XML de config).
public class MvcConfig implements WebMvcConfigurer {
// Déclare la classe MvcConfig qui implémente WebMvcConfigurer pour personnaliser Spring MVC.

    public void addViewControllers(ViewControllerRegistry registry) {
        // Méthode permettant d’ajouter des "view controllers" simples.
        // Un view controller est un mapping direct entre une URL et une vue, sans passer par un contrôleur Java.

        registry.addViewController("/signup").setViewName("signup");
        // Associe l’URL "/signup" directement à la vue "signup".
        // Quand un utilisateur accède à /signup, Spring renvoie la page signup.html sans logique supplémentaire.
    }
}

