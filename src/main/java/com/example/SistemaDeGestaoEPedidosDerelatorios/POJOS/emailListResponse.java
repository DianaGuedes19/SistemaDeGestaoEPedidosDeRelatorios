package com.example.SistemaDeGestaoEPedidosDerelatorios.POJOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class emailListResponse {

    @Getter
    @Setter
    private List<String> validEmails;


}
