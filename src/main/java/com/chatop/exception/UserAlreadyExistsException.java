package com.chatop.exception;

/**
 * Exception levée lorsqu'un utilisateur tente de s'inscrire avec
 * une adresse e-mail déjà utilisée.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Crée une nouvelle exception avec le message fourni.
     *
     * @param message détail du problème rencontré
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
