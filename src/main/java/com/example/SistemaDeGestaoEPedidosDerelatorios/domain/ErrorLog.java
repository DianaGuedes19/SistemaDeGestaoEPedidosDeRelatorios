package com.example.SistemaDeGestaoEPedidosDerelatorios.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "error_log")
@AllArgsConstructor
@NoArgsConstructor
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Getter
    @Setter
    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")

    private String message;

    @Getter
    @Setter
    private LocalDateTime timestamp;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = true, foreignKey = @ForeignKey(name = "FK_Error_Order"))
    private Order order;

}
