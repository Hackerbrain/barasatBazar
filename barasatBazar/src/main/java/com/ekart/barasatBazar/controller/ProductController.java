package com.ekart.barasatBazar.controller;

import com.ekart.barasatBazar.dto.ProductDTO;
import com.ekart.barasatBazar.entity.ProductEntity;
import com.ekart.barasatBazar.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public String createProduct(@RequestBody ProductDTO product) {
        ProductDTO savedProduct = productService.createProduct(product);
        return "Product created with IDs: "+ savedProduct;
    }
}
