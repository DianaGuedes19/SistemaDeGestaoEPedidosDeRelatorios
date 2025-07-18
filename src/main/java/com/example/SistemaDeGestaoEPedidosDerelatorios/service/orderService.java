package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;

import java.time.LocalDate;
import java.util.List;

public interface orderService {

    orderDTOResponse createOrder (orderDTORequest order);
    List<orderDTOResponse> getAllOders ();
    orderDTOResponse getOrderByID (Long id);
    List<orderDTOResponse> findByStatus (State status);
    List<orderDTOResponse> findByCreationDate (LocalDate date);
}
