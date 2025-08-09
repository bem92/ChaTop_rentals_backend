package com.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @Schema(example = "user@example.com")
    private String login;

    @Schema(example = "Password123!")
    private String password;
}

