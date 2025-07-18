package com.example.SistemaDeGestaoEPedidosDerelatorios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class securityConfig {

    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // todas as chamadas exigem um Bearer token válido
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/orders/**").authenticated()
                        .anyRequest().permitAll()
                )
                // habilita o suporte a JWT como Bearer tokens
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults())
                );

        return http.build();
    }
}
