package com.chatop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequest {
    private String email;
    private String name;    // ✅ Pour register
    private String login;   // ✅ Pour login selon Postman
    private String password;
}