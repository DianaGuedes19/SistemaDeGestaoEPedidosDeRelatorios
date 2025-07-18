package com.example.SistemaDeGestaoEPedidosDerelatorios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfig {

    private final validationProperties validationProps;

    public securityConfig(validationProperties validationProps) {
        this.validationProps = validationProps;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(csrf -> csrf.disable())

                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 3) Regras de autorização
                .authorizeHttpRequests(auth -> auth
                        // libera TODOS os GET em toda a app
                        .requestMatchers(HttpMethod.GET).permitAll()
                        // exige autenticação para qualquer outro metodo em /api/**
                        .requestMatchers(HttpMethod.POST, "/orders/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,  "/order/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/order/**").authenticated()
                        // resto da app é público
                        .anyRequest().permitAll()
                )

                // 4) Validar Bearer Tokens
                .oauth2ResourceServer(o2 -> o2
                        .opaqueToken(token -> token
                                .introspectionUri(validationProps.getUrl())
                                .introspectionClientCredentials(
                                        validationProps.getClientId(),
                                        validationProps.getClientSecret()
                                )
                        )
                );

        return http.build();
    }
}
