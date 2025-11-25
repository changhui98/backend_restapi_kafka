package com.gbg.productservice.infrastructre.repository;

import com.gbg.productservice.domain.entity.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {

}
