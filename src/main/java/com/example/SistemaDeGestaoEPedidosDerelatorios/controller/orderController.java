package com.example.SistemaDeGestaoEPedidosDerelatorios.controller;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.service.orderService;
import com.example.SistemaDeGestaoEPedidosDerelatorios.service.orderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class orderController {

    private final orderService orderService1;

    public orderController(orderService orderService) {
        this.orderService1 = orderService;
    }

    @PostMapping()
    public ResponseEntity<orderDTOResponse> createOrder (@RequestBody orderDTORequest orderRequest){
        return new ResponseEntity<>(orderService1.createOrder(orderRequest),HttpStatus.CREATED);
    }

    @GetMapping("/allOrders")
    public ResponseEntity<List<orderDTOResponse>> getAllOrders (){
        return new ResponseEntity<>(orderService1.getAllOders(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<orderDTOResponse> getOrderByID (@PathVariable Long id){
        return new ResponseEntity<>(orderService1.getOrderByID(id),HttpStatus.OK);
    }
}
