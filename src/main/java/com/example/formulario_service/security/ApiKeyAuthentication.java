package com.example.formulario_service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthentication implements ReactiveAuthenticationManager {

    @Value("${app.security.api-key}")
    private String validApiKey;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String apiKey = authentication.getCredentials().toString();

        if (validApiKey.equals(apiKey)) {
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    "api-user",
                    apiKey,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_API"))
            );
            return Mono.just(auth);
        }

        return Mono.empty();
    }
}
