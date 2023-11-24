package com.shubham.orderservice.service;
import com.shubham.orderservice.dto.OrderLineItemDto;
import com.shubham.orderservice.dto.OrderRequestDto;
import com.shubham.orderservice.entity.Orders;
import com.shubham.orderservice.entity.OrderLineItems;
import com.shubham.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequestDto orderRequestDto)
    {

        Orders orders = new Orders();
        orders.setOrderNumber(UUID.randomUUID().toString());
        orders.setOrderLineItems(orderRequestDto.getOrderLineItemDto().stream().map(this::mapFromDto).toList());

        // if in stock inventory check - inventory service

        orderRepository.save(orders);
        log.info("Order Placed");
    }

    private OrderLineItems mapFromDto(OrderLineItemDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }


}
