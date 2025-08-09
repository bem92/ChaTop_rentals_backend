package com.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @Schema(example = "user@example.com")
    private String email;

    @Schema(example = "John Doe")
    private String name;

    @Schema(example = "Password123!")
    private String password;
}

