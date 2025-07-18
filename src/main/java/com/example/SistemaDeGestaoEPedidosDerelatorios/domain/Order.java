package com.example.SistemaDeGestaoEPedidosDerelatorios.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "orderId")
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
    @Column(name = "CreationDate", nullable = false)
    private LocalDate creationDate;

    @Getter
    @Setter
    @NotNull (message = "Status cannot be null")
    @Column(name = "Status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private State status;

    @Getter
    @Setter
    @NotNull (message = "Value cannot be null")
    @Positive(message = "Value cannot be 0 or below")
    @Column(name = "Value", nullable = false)
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderStatusHistory> history = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ErrorLog> errorLog = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClientId", nullable = false)
    @Getter @Setter
    private Client client;



}
