package com.chatop.service;

import com.chatop.dto.MessageRequest;
import com.chatop.model.Message;
import com.chatop.model.Rental;
import com.chatop.model.User;
import com.chatop.repository.MessageRepository;
import com.chatop.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for handling messages related to rentals.
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;

    @Transactional
    public void sendMessage(MessageRequest request) {
        Rental rental = rentalRepository.findById(request.getRentalId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new RuntimeException("Not authenticated");
        }

        Message message = Message.builder()
                .rental(rental)
                .user(user)
                .message(request.getMessage())
                .build();

        messageRepository.save(message);
    }
}
