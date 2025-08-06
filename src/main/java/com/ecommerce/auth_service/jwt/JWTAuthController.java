package com.ecommerce.auth_service.jwt;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class JWTAuthController {

    @PostMapping("/login")
    public String login() {
        return "Hello users";
    }
}
