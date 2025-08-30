package com.ekart.barasatBazar.controller;

import com.ekart.barasatBazar.dto.ProductDTO;
import com.ekart.barasatBazar.entity.ProductEntity;
import com.ekart.barasatBazar.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createProduct(@RequestBody ProductDTO product) {
        ProductDTO savedProduct = productService.createProduct(product);
        return ResponseHandler.response(HttpStatus.OK, savedProduct, "Product created with IDs");
    }

    @PutMapping("/add")
    public ResponseEntity<CommonResponse> addProduct(@RequestParam long id, @RequestBody ProductDTO product) {
        ProductDTO addProduct = productService.addProduct(id, product);
        return ResponseHandler.response(HttpStatus.OK, addProduct, "Product added with same IDs");
    }
}
