package com.tech.productservice.repository;

import com.tech.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends JpaRepository<Product,String> {
}
