package com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.errorLog;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class errorLogDTOResponse {

    @Getter
    private Long id;

    @Getter
    @Setter
    private LocalDateTime occurredAt;

    @Getter
    @Setter
    private String path;

    @Getter
    @Setter
    private String exception;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String stackTrace;
}
