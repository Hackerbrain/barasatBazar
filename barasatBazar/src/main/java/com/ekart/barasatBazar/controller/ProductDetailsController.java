package com.ekart.barasatBazar.controller;

import com.ekart.barasatBazar.dto.ProductDTO;
import com.ekart.barasatBazar.dto.ProductDetailsDTO;
import com.ekart.barasatBazar.service.ProductService;
import com.personal.common.dto.CommonResponse;
import com.personal.common.handler.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productDetails")
@Tag(name= "ProductDetails Controller",description = "ProductDetails Related Activity")
public class ProductDetailsController {

    @Autowired
    private ProductService productService;

    /**
     * Add product details response entity.
     *
     * @param productId      the product id
     * @param productDetails the product details
     * @return the response entity
     */
    @PutMapping("/add")
    @Operation( summary = "Add new ProductDetails", description = "adding new productDetails under existing product")
    public ResponseEntity<CommonResponse> addProductDetails(@RequestParam String productId,
                                                            @RequestBody ProductDetailsDTO productDetails) {
        ProductDTO savedProduct = productService.addProductDetails(productId, productDetails);
        return ResponseHandler.response(HttpStatus.OK, savedProduct, "Product added with same IDs");
    }
}
