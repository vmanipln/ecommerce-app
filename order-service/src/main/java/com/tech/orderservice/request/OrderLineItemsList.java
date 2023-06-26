package com.tech.orderservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsList {

    private long id;
    private String skuCode;
    private BigDecimal price;
    private int quantity;
}
