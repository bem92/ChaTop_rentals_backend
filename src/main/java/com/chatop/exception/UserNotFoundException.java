package com.chatop.exception;

/**
 * Exception levée lorsqu'aucun utilisateur n'est trouvé pour les
 * critères de recherche fournis.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Crée une nouvelle exception avec le message fourni.
     *
     * @param message détail du problème rencontré
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
