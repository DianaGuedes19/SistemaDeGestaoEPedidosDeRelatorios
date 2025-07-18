package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.POJOS.emailListResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import com.example.SistemaDeGestaoEPedidosDerelatorios.mapper.orderMapper;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.orderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.SistemaDeGestaoEPedidosDerelatorios.mapper.orderMapper.toDTOResponse;

@Service
public class orderServiceImpl implements orderService {

    private final orderRepository orderRepository1;
    private final RestTemplate restTemplate;
    private final String listUrl;

    public orderServiceImpl(orderRepository orderRepository1, RestTemplate restTemplate,  @Value("${validation.listUrl}") String listUrl) {
        this.orderRepository1 = orderRepository1;
        this.restTemplate = restTemplate;

        this.listUrl = listUrl;
    }

    @Override
    public orderDTOResponse createOrder(orderDTORequest order) {

        emailListResponse listResp = restTemplate.getForObject(listUrl, emailListResponse.class);

        assert listResp != null;
        List<String> valid = listResp.getValidEmails();

        boolean exists = valid.contains(order.getClientEmail());
        Order order1 = orderMapper.toOrderEntity(order);

        if (!exists){
            order1.setClientValid(false);
            order1.setValidationMessage("Client doesn't exists");
        }
        else {
        order1.setClientValid(true);
        order1.setValidationMessage("Existing Client. Order created with success!"); }

        orderRepository1.save(order1);
        return toDTOResponse(order1);
    }


    @Override
    public List<orderDTOResponse> getAllOders() {

        List<Order> order = orderRepository1.findAll();
        return order.stream().map(orderMapper::toDTOResponse).collect(Collectors.toList());
    }

    @Override
    public orderDTOResponse getOrderByID(Long id) {

        Order order1 = orderRepository1.findById(id).orElseThrow(() -> new RuntimeException("Order not found " + id));

        return toDTOResponse(order1);
    }

    @Override
    public List<orderDTOResponse> findByState(State status) {
        return orderRepository1.findByState(status).stream().map(orderMapper::toDTOResponse).collect(Collectors.toList());
    }
}
