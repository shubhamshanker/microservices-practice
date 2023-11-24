package com.shubham.productservice.controller;

import com.shubham.productservice.dto.ProductReq;
import com.shubham.productservice.dto.ProductRes;
import com.shubham.productservice.entity.Product;
import com.shubham.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    @GetMapping("/all")
    private List<ProductRes> getAllProduct() {
        return productService.getAllProducts();
    }

    @PostMapping("/create")
    private Product createProduct(@RequestBody ProductReq productReq) {
        return productService.saveproduct(productReq);
    }
}
