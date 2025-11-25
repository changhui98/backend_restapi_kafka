package com.gbg.productservice.application.service;

import com.gbg.productservice.domain.entity.Product;
import com.gbg.productservice.infrastructre.repository.ProductJpaRepository;
import com.gbg.productservice.presentation.dto.request.ProductCreateRequest;
import com.gbg.productservice.presentation.dto.response.ProductResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductJpaRepository productJpaRepository;

    @PostMapping
    public UUID createProduct(@RequestBody ProductCreateRequest productCreateRequest) {

        Product product = Product.of(productCreateRequest.productCode(), productCreateRequest.qty(),
            productCreateRequest.unitPrice());

        productJpaRepository.save(product);
        return product.getId();
    }

    public List<ProductResponse> getProducts() {

        List<Product> productList = productJpaRepository.findAll();

        return productList.stream()
            .map(p -> new ProductResponse(
                p.getId(),
                p.getProductCode(),
                p.getQty(),
                p.getUnitPrice()
            )).collect(Collectors.toList());
    }
}
