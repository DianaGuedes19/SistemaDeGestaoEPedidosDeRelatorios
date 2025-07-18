package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.POJOS.ValidationResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.POJOS.validationRequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.mapper.orderMapper;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.orderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class orderServiceImpl implements orderService {

    private final orderRepository orderRepository1;
    private final RestTemplate restTemplate;
    private final String validationUrl;

    public orderServiceImpl(orderRepository orderRepository1, RestTemplate restTemplate, @Value("${spring.validation.url}") String validationUrl) {
        this.orderRepository1 = orderRepository1;
        this.restTemplate = restTemplate;
        this.validationUrl = validationUrl;
    }

    @Override
    public orderDTOResponse createOrder(orderDTORequest order) {

        // DTO -> Validation Request
        validationRequest vr = new validationRequest(order.getClientName(), order.getClientEmail());

        ValidationResponse resp;
        try {
            ResponseEntity<ValidationResponse> response = restTemplate.postForEntity(validationUrl, vr, ValidationResponse.class);
            resp = response.getBody();
        } catch (RestClientException ex) {
            // timeout, falha de rede…
            resp = new ValidationResponse(false, "Validation Error: " + ex.getMessage());
        }

        Order order1 = orderMapper.toOrderEntity(order);

        order1.setClientValid( resp != null && resp.getValid());
        order1.setValidationMessage( resp != null ? resp.getReason() : "No Validation asnwer" );


        orderRepository1.save(order1);

        return orderMapper.toDTOResponse(order1);
    }

    @Override
    public List<orderDTOResponse> getAllOders() {

        List<Order> order = orderRepository1.findAll();
        return order.stream().map(orderMapper::toDTOResponse).collect(Collectors.toList());
    }

    @Override
    public orderDTOResponse getOrderByID(Long id) {

        Order order1 = orderRepository1.findById(id).orElseThrow(() -> new RuntimeException("Order not found " + id));

        return orderMapper.toDTOResponse(order1);
    }
}
