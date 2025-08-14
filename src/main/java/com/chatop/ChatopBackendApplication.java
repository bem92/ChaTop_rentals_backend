package com.chatop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot.
 */
@SpringBootApplication // Active la configuration automatique et le scan des composants
public class ChatopBackendApplication {

        /**
         * Méthode principale lancée au démarrage de l'application.
         *
         * @param args arguments éventuels de la ligne de commande
         */
        public static void main(String[] args) {
                SpringApplication.run(ChatopBackendApplication.class, args);
        }

}
