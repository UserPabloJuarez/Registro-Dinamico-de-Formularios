package com.example.formulario_service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyAuthConverter implements ServerAuthenticationConverter {

    private static final String API_KEY_HEADER = "X-API-Key";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(API_KEY_HEADER))
                .filter(apiKey -> !apiKey.isBlank())
                .map(apiKey -> new ApiKeyAuthToken(apiKey));
    }

    private static class ApiKeyAuthToken extends org.springframework.security.authentication.UsernamePasswordAuthenticationToken {
        public ApiKeyAuthToken(String apiKey) {
            super("api-user", apiKey);
        }
    }
}
