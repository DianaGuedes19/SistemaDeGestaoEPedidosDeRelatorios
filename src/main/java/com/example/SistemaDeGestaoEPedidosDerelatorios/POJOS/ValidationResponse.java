package com.example.SistemaDeGestaoEPedidosDerelatorios.POJOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ValidationResponse {

    @Getter
    @Setter
    private Boolean valid;

    @Getter
    @Setter
    private String reason;


}
