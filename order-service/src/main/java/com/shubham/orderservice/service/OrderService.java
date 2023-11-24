package com.shubham.orderservice.service;
import com.shubham.orderservice.dto.InventoryResDto;
import com.shubham.orderservice.dto.OrderLineItemDto;
import com.shubham.orderservice.dto.OrderRequestDto;
import com.shubham.orderservice.entity.Orders;
import com.shubham.orderservice.entity.OrderLineItems;
import com.shubham.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;
    public void placeOrder(OrderRequestDto orderRequestDto) throws IllegalAccessException {

        Orders orders = new Orders();
        orders.setOrderNumber(UUID.randomUUID().toString());
        orders.setOrderLineItems(orderRequestDto.getOrderLineItemDto().stream().map(this::mapFromDto).toList());

        List<String> skuCodes = orderRequestDto.getOrderLineItemDto().stream().map(OrderLineItemDto::getSkuCode).toList();

        InventoryResDto[] inventoryRes = webClient.get()
                        .uri("http://localhost:8083/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                                .retrieve()
                                        .bodyToMono(InventoryResDto[].class)
                                                .block();

        // for all skucodes - check if isinStock
        List<String> skuCodeRes = Arrays.stream(inventoryRes).map(InventoryResDto::getSkuCode).toList();
        boolean allProductsInInventory = skuCodes.stream().allMatch(skuCodeRes::contains);
        boolean allProductsInStock = Arrays.stream(inventoryRes).allMatch(InventoryResDto::isInStock);

        // if in stock inventory check - inventory service
        if(allProductsInStock && allProductsInInventory) {
            orderRepository.save(orders);
            log.info("Order Placed");
        }
        else throw new IllegalAccessException("Product Not in Stock");

    }

    private String getSkuCode(OrderLineItemDto orderLineItemDto) {
        return orderLineItemDto.getSkuCode();
    }

    private OrderLineItems mapFromDto(OrderLineItemDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
