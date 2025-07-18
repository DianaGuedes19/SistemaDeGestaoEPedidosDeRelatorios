package com.example.SistemaDeGestaoEPedidosDerelatorios.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ErrorLog")
@AllArgsConstructor
@NoArgsConstructor
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "ErrorId")
    private Long id;

    @Getter
    @Setter
    @Column(name = "Message", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String message;

    @Getter
    @Setter
    @Column(name = "LoggedAt", nullable = false)
    private LocalDate loggedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderId", nullable = true, foreignKey = @ForeignKey(name = "FK_Error_Order"))
    private Order order;

}
