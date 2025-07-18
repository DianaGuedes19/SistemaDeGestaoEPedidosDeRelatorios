package com.example.SistemaDeGestaoEPedidosDerelatorios.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "OrderStatusHistory")
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "HistoryId")
    private Long id;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "OldStatus", nullable = false, length = 50)
    private State oldStates;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "NewStatus", nullable = false, length = 50)
    private State newState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderId", nullable = false, foreignKey = @ForeignKey(name = "FK_History_Order"))
    private Order order;



}
