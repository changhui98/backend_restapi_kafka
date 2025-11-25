package com.gbg.productservice.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String productCode;

    private int qty;

    private int unitPrice;

    public void updateQty(int qty) {
        this.qty = this.qty - qty;
    }

    public static Product of(String productCode, int qty, int unitPrice) {
        Product product = new Product();
        product.productCode = productCode;
        product.qty = qty;
        product.unitPrice = unitPrice;
        return product;
    }

}
