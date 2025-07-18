package com.example.SistemaDeGestaoEPedidosDerelatorios.POJOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class validationRequest {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

}
