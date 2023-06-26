package com.tech.orderservice.service;

import com.tech.orderservice.model.Order;
import com.tech.orderservice.model.OrderLineItems;
import com.tech.orderservice.repository.OrderRepository;
import com.tech.orderservice.request.OrderLineItemsList;
import com.tech.orderservice.request.OrderRequest;
import com.tech.orderservice.response.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final WebClient.Builder webClientBuilder;
    private final OrderRepository orderRepository;

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsListList()
                .stream()
                .map(this::mapToOrderLineItem)
                .collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                        .map(OrderLineItems::getSkuCode)
                                .collect(Collectors.toList());



        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                        .retrieve()
                                .bodyToMono(InventoryResponse[].class)
                                        .block();



        boolean allProductInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        log.info("allProductInStock"+allProductInStock);
        if(allProductInStock){
            orderRepository.save(order);
            return "Order Placed Successfully";
        } else{
            throw new IllegalArgumentException("Product is not in stock");
        }
    }

    private OrderLineItems mapToOrderLineItem(OrderLineItemsList orderLineItemsList){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsList.getPrice());
        orderLineItems.setQuantity(orderLineItems.getQuantity());
        orderLineItems.setSkuCode(orderLineItems.getSkuCode());
        return orderLineItems;
    }
}
