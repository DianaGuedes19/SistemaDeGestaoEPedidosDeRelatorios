package com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class clientDTORequest {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;
}
