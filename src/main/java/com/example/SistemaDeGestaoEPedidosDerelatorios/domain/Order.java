package com.example.SistemaDeGestaoEPedidosDerelatorios.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
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
    private LocalDate creationDate;

    @Getter
    @Setter
    @NotNull (message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    private State status;

    @Getter
    @Setter
    @NotNull (message = "Value cannot be null")
    @Positive(message = "Value cannot be 0 or below")
    private double value;

    @Getter
    @Setter
    private Boolean clientValid;

    @Getter
    @Setter
    private String validationMessage;



    // Constructor with the firsts parameters before the validation of users
    public Order(Long id, String clientName, String clientEmail, LocalDate creationDate, State status, double value) {
        this.id = id;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.creationDate = creationDate;
        this.status = status;
        this.value = value;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
