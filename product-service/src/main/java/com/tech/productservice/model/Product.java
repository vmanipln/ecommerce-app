package com.tech.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Product {
    @Id
    @Column
    private String id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private BigDecimal price;

}
