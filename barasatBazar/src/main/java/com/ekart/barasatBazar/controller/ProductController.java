package com.ekart.barasatBazar.controller;

import com.ekart.barasatBazar.dto.ProductDTO;
import com.ekart.barasatBazar.dto.ProductDetailsDTO;
import com.ekart.barasatBazar.dto.SearchDTO;
import com.ekart.barasatBazar.entity.ProductEntity;
import com.ekart.barasatBazar.service.ProductService;
import com.personal.common.dto.CommonResponse;
import com.personal.common.handler.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Product controller.
 */
@RestController
@RequestMapping("/api/product")
@Tag(name= "Product Controller",description = "Product Related Activity")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    @Operation(summary = "Create Product",description = "Creating a new product")
    public ResponseEntity<CommonResponse> createProduct(@RequestBody ProductDTO product) {
        ProductDTO savedProduct = productService.createProduct(product);
        return ResponseHandler.response(HttpStatus.OK, savedProduct, "Product created with IDs");
    }

    @GetMapping
    @Operation(summary = "Show All Product",description = "Showing all existing products with product Details")
    public ResponseEntity<CommonResponse> getAllProducts() {
        List<ProductDTO> productList = productService.getAllProducts();
        return ResponseHandler.response(HttpStatus.OK, productList, "Product List fetched Successfully");
    }

    @PostMapping("/search")
    @Operation(summary = "Search Product",description = "Searching existing product")
    public ResponseEntity<CommonResponse> searchProductDetails(@RequestBody SearchDTO searchParams,
                                                               @PageableDefault(size = 10,sort = "productName") Pageable pageable) {
        List<ProductDTO> productList = productService.searchProductDetails(searchParams, pageable);
        return ResponseHandler.response(HttpStatus.OK, productList, "Product List fetched Successfully");
    }

    @PutMapping("/update")
    @Operation(summary = "Modify Product",description = "Modifying existing product")
    public ResponseEntity<CommonResponse> updateProduct(@RequestBody ProductDTO product) {
        ProductDTO updatedProduct = productService.updateProduct(product);
        return ResponseHandler.response(HttpStatus.OK, updatedProduct, "Product List fetched Successfully");
    }


}

