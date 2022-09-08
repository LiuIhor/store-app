package com.example.finalprojectstoreapp.mappers;

import com.example.finalprojectstoreapp.dtos.order.OrderDto;
import com.example.finalprojectstoreapp.models.Order;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

@UtilityClass
public class OrderMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public Order convertToEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    public OrderDto convertToDTO(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}