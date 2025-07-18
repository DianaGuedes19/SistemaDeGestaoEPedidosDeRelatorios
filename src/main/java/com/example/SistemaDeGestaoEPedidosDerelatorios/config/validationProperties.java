package com.example.SistemaDeGestaoEPedidosDerelatorios.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "validation")
public class validationProperties {

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String listUrl;

    @Getter
    @Setter
    private String clientId;

    @Getter
    @Setter
    private String clientSecret;


}
