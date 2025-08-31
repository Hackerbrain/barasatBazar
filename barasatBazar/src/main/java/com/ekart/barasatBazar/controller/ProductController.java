package com.ekart.barasatBazar.controller;

import com.ekart.barasatBazar.dto.ProductDTO;
import com.ekart.barasatBazar.dto.ProductDetailsDTO;
import com.ekart.barasatBazar.service.ProductService;
import com.personal.common.dto.CommonResponse;
import com.personal.common.handler.ResponseHandler;
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

    @PutMapping("/productDetails/add")
    public ResponseEntity<CommonResponse> addProductDetails(@RequestParam long productId, @RequestBody ProductDetailsDTO productDetails) {
        ProductDetailsDTO addedProductDetails = productService.addProductDetails(productId,productDetails);
        return ResponseHandler.response(HttpStatus.OK, addedProductDetails, "Product added with same IDs");
    }
}
