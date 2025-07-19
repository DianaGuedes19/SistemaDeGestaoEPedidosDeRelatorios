package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.POJOS.emailListResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import com.example.SistemaDeGestaoEPedidosDerelatorios.mapper.orderMapper;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.orderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.SistemaDeGestaoEPedidosDerelatorios.mapper.orderMapper.toDTOResponse;

@Service
public class orderServiceImpl implements orderService {

    private final orderRepository orderRepository1;
    private final RestTemplate restTemplate;
    private final String listUrl;
    private final ErrorLogService errorLogService;

    public orderServiceImpl(orderRepository orderRepository1, RestTemplate restTemplate,  @Value("${validation.listUrl}") String listUrl, ErrorLogService errorLogService) {
        this.orderRepository1 = orderRepository1;
        this.restTemplate = restTemplate;
        this.errorLogService = errorLogService;
        this.listUrl = listUrl;
    }

    @Override
    public orderDTOResponse createOrder(orderDTORequest order) {

        try {
            emailListResponse listResp = restTemplate.getForObject(listUrl, emailListResponse.class);
            assert listResp != null;
            List<String> valid = listResp.getValidEmails();

            boolean exists = valid.contains(order.getClientEmail());
            Order orderEntity = orderMapper.toOrderEntity(order);

            if (!exists) {
                throw new IllegalArgumentException("Client does not exist. Invalid email.");
            } else {
                orderEntity.setClientValid(true);
                orderEntity.setValidationMessage("Existing Client. Order created with success!");
            }

            orderRepository1.save(orderEntity);
            return orderMapper.toDTOResponse(orderEntity);

        } catch (Exception e) {
            errorLogService.logError(e, null);
            throw e;
        }
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
    public List<orderDTOResponse> findByStatus(State status) {
        return orderRepository1.findByStatus(status).stream().map(orderMapper::toDTOResponse).collect(Collectors.toList());
    }

    @Override
    public List<orderDTOResponse> findByCreationDate(LocalDate date) {
        return orderRepository1.findByCreationDate(date).stream().map(orderMapper::toDTOResponse).collect(Collectors.toList());
    }
}
