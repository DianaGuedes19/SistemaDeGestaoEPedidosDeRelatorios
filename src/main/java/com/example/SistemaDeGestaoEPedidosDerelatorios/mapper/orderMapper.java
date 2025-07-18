package com.example.SistemaDeGestaoEPedidosDerelatorios.mapper;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;

public class orderMapper {

    public static orderDTOResponse toDTOResponse(Order order){
        orderDTOResponse orderDTOResponse1 = new orderDTOResponse();

        orderDTOResponse1.setId(order.getId());
        orderDTOResponse1.setClientName(order.getClientName());
        orderDTOResponse1.setClientEmail(order.getClientEmail());
        orderDTOResponse1.setCreationDate(order.getCreationDate());
        orderDTOResponse1.setStatus(order.getStatus());
        orderDTOResponse1.setValue(order.getValue());
        orderDTOResponse1.setClientValid(order.getClientValid());
        orderDTOResponse1.setValidationMessage(order.getValidationMessage());

        return orderDTOResponse1;
    }


    public static Order toOrderEntity (orderDTORequest orderDTORequest){
         Order order1 = new Order();

         order1.setClientEmail(orderDTORequest.getClientEmail());
         order1.setClientName(orderDTORequest.getClientName());
         order1.setCreationDate(orderDTORequest.getCreationDate());
         order1.setStatus(orderDTORequest.getStatus());
         order1.setValue(orderDTORequest.getValue());

         return order1;
    }
}
