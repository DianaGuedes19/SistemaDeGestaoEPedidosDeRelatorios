package com.example.SistemaDeGestaoEPedidosDerelatorios.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    @NotNull (message = "Client name cannot be null")
    @NotBlank (message = "Client name cannot be in blank")
    private String clientName;

    @Getter
    @Setter
    @NotNull (message = "Client email cannot be null")
    @NotBlank (message = "Client email cannot be in blank")
    @Email (message = "Email format is wrong, should contain @ and domain")
    private String clientEmail;

    @Getter
    @Setter
    @NotNull (message = "LocalDate cannot be null")
    @NotBlank (message = "LocalDate cannot be in blank")
    private LocalDate creationDate;

    @Getter
    @Setter
    @NotNull (message = "Status cannot be null")
    @NotBlank (message = "Status cannot be in blank")
    @Enumerated(EnumType.STRING)
    private State status;

    @Getter
    @Setter
    @NotNull (message = "Value cannot be null")
    @NotBlank (message = "Value cannot be in blank")
    @PositiveOrZero(message = "Value cannot be 0 or below")
    private double value;


}
