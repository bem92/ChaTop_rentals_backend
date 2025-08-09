package com.chatop.controller;

import com.chatop.dto.MessageRequest;
import com.chatop.dto.MessageResponse;
import com.chatop.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for sending messages about rentals.
 */
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @Operation(summary = "Send a message to rental owner")
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageRequest request) {
        messageService.sendMessage(request);
        return ResponseEntity.ok(new MessageResponse("Message send with success"));
    }
}
