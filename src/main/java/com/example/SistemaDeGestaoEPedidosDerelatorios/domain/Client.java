package com.example.SistemaDeGestaoEPedidosDerelatorios.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Client")
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "ClientId")
    private Long id;

    @Getter
    @Setter
    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @Getter
    @Setter
    @Column(name = "Email", nullable = false, length = 150, unique = true)
    @Email
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();


}
