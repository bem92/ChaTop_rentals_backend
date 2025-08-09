package com.chatop.controller;

import com.chatop.dto.MessageRequest;
import com.chatop.dto.MessageResponse;
import com.chatop.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@Tag(name = "Messages", description = "Gestion des messages envoyés aux propriétaires")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @Operation(
        summary = "Envoyer un message",
        description = "Envoie un message au propriétaire d'une location",
        responses = {
            @ApiResponse(responseCode = "200", description = "Message envoyé",
                content = @Content(schema = @Schema(implementation = MessageResponse.class)))
        }
    )
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageRequest request) {
        messageService.sendMessage(request);
        return ResponseEntity.ok(new MessageResponse("Message send with success"));
    }
}
