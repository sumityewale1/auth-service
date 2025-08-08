package com.ecommerce.auth_service.jwt.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// dto/AuthRequest.java
@Data
@Getter
@Setter
public class AuthRequest {
    private String username;
    private String password;
}
