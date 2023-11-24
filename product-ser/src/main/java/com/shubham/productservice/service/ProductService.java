package com.shubham.productservice.service;

import com.shubham.productservice.dto.ProductReq;
import com.shubham.productservice.dto.ProductRes;
import com.shubham.productservice.entity.Product;
import com.shubham.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Product saveproduct(ProductReq productReq) {
        Product product = Product.builder()
                .name(productReq.getName())
                .description(productReq.getDescription())
                .price(productReq.getPrice())
                .build();
        log.info("Product to be save : {}", product);
        productRepository.save(product);
        log.info("Saved Product : {}", product);
        return product;
    }

    public List<ProductRes> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(this::getProductResponse).toList();
    }

    public ProductRes getProductResponse(Product p) {
        return ProductRes.builder().
                id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .build();
    }
}
