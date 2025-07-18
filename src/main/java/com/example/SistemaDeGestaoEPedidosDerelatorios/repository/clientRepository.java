package com.example.SistemaDeGestaoEPedidosDerelatorios.repository;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface clientRepository extends JpaRepository<Client,Long> {
}
