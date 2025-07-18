package com.example.SistemaDeGestaoEPedidosDerelatorios.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(name = "OccurredAt", nullable = false)
    private LocalDateTime occurredAt;

    @Getter
    @Setter
    @Column(name = "Path", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String path;

    @Getter
    @Setter
    @Column(name = "Exception", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String exception;

    @Getter
    @Setter
    @Column(name = "Message", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String message;

    @Getter
    @Setter
    @Column(name = "StackTrace", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String stackTrace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderId", nullable = true, foreignKey = @ForeignKey(name = "FK_Error_Order"))
    private Order order;

}
