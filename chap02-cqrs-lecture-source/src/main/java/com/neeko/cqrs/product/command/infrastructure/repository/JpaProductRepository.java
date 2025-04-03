package com.neeko.cqrs.product.command.infrastructure.repository;

import com.neeko.cqrs.product.command.domain.aggregate.Product;
import com.neeko.cqrs.product.command.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, Long> {

}
