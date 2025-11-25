package com.gbg.productservice.presentation.controller;

import com.gbg.productservice.application.service.ProductService;
import com.gbg.productservice.domain.entity.Product;
import com.gbg.productservice.presentation.dto.request.ProductCreateRequest;
import com.gbg.productservice.presentation.dto.response.ProductResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<UUID> createProduct(@RequestBody ProductCreateRequest productCreateRequest) {

        UUID productId = productService.createProduct(productCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {

        List<ProductResponse> productList =  productService.getProducts();

        return ResponseEntity.ok().body(productList);
    }

}
