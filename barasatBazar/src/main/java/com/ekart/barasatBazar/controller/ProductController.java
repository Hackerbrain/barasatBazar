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

    // 🔹 Create product
    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createProduct(@RequestBody ProductDTO product) {
        ProductDTO savedProduct = productService.createProduct(product);
        return ResponseHandler.response(HttpStatus.OK, savedProduct, "Product created with IDs");
    }

    // 🔹 Add product details
    @PutMapping("/productDetails/add")
    public ResponseEntity<CommonResponse> addProductDetails(@RequestParam long productId,
                                                            @RequestBody ProductDetailsDTO productDetails) {
        ProductDetailsDTO addedProductDetails = productService.addProductDetails(productId, productDetails);
        return ResponseHandler.response(HttpStatus.OK, addedProductDetails, "Product added with same IDs");
    }

    // 🔹 fetch product details
    @GetMapping("/details/fetch")
    public ResponseEntity<CommonResponse> getByBasic(@RequestParam String productName,
                                                     @RequestParam String companyName,
                                                     @RequestParam String category) {
        List<ProductDetailsDTO> result = productService.getByBasic(productName, companyName, category);
        return ResponseHandler.response(HttpStatus.OK, result, "Fetched products with basic filters");
    }

    // 2) With size
    @GetMapping("/details/withSize/fetch")
    public ResponseEntity<CommonResponse> getBySize(@RequestParam String productName,
                                                    @RequestParam String companyName,
                                                    @RequestParam String category,
                                                    @RequestParam String size) {
        List<ProductDetailsDTO> result = productService.getBySize(productName, companyName, category, size);
        return ResponseHandler.response(HttpStatus.OK, result, "Fetched products with size filter");
    }

    // 3) With color
    @GetMapping("/details/withColor/fetch")
    public ResponseEntity<CommonResponse> getByColor(@RequestParam String productName,
                                                     @RequestParam String companyName,
                                                     @RequestParam String category,
                                                     @RequestParam String color) {
        List<ProductDetailsDTO> result = productService.getByColor(productName, companyName, category, color);
        return ResponseHandler.response(HttpStatus.OK, result, "Fetched products with color filter");
    }

    // 4) With size & price (<= price)
    @GetMapping("/details/withSizeAndPrice/fetch")
    public ResponseEntity<CommonResponse> getBySizeAndPrice(@RequestParam String productName,
                                                            @RequestParam String companyName,
                                                            @RequestParam String category,
                                                            @RequestParam String size,
                                                            @RequestParam Double price) {
        List<ProductDetailsDTO> result = productService.getBySizeAndPrice(productName, companyName, category, size, price);
        return ResponseHandler.response(HttpStatus.OK, result, "Fetched products with size & price filters");
    }

    // 5) With color & price (<= price)
    @GetMapping("/details/withColorAndPrice/fetch")
    public ResponseEntity<CommonResponse> getByColorAndPrice(@RequestParam String productName,
                                                             @RequestParam String companyName,
                                                             @RequestParam String category,
                                                             @RequestParam String color,
                                                             @RequestParam Double price) {
        List<ProductDetailsDTO> result = productService.getByColorAndPrice(productName, companyName, category, color, price);
        return ResponseHandler.response(HttpStatus.OK, result, "Fetched products with color & price filters");
    }

    // 1) Update by productId
    @PutMapping("/stock/update/byProductId")
    public ResponseEntity<CommonResponse> updateStockByProductId(@RequestParam Long productId,
                                                                 @RequestBody ProductDetailsDTO dto) {
        productService.updateStockByProductId(productId, dto);
        return ResponseHandler.response(HttpStatus.OK, null, "Stock updated by productId");
    }

    // 2) Update by productId & detailId
    @PutMapping("/stock/update/byProductAndDetail")
    public ResponseEntity<CommonResponse> updateStockByProductIdAndDetailId(@RequestParam Long productId,
                                                                            @RequestParam Long detailId,
                                                                            @RequestBody ProductDetailsDTO dto) {
        productService.updateStockByProductIdAndDetailId(productId, detailId, dto);
        return ResponseHandler.response(HttpStatus.OK, null, "Stock updated by productId & detailId");
    }

    // 3) Update by basic info
    @PutMapping("/stock/update/byBasic")
    public ResponseEntity<CommonResponse> updateStockByBasic(@RequestParam String productName,
                                                             @RequestParam String companyName,
                                                             @RequestParam String category,
                                                             @RequestBody ProductDetailsDTO dto) {
        productService.updateStockByBasic(productName, companyName, category, dto);
        return ResponseHandler.response(HttpStatus.OK, null, "Stock updated by basic info");
    }

    // 4) Update by size
    @PutMapping("/stock/update/bySize")
    public ResponseEntity<CommonResponse> updateStockBySize(@RequestParam String productName,
                                                            @RequestParam String companyName,
                                                            @RequestParam String category,
                                                            @RequestParam String size,
                                                            @RequestBody ProductDetailsDTO dto) {
        productService.updateStockBySize(productName, companyName, category, size, dto);
        return ResponseHandler.response(HttpStatus.OK, null, "Stock updated by size");
    }

    // 5) Update by color
    @PutMapping("/stock/update/byColor")
    public ResponseEntity<CommonResponse> updateStockByColor(@RequestParam String productName,
                                                             @RequestParam String companyName,
                                                             @RequestParam String category,
                                                             @RequestParam String color,
                                                             @RequestBody ProductDetailsDTO dto) {
        productService.updateStockByColor(productName, companyName, category, color, dto);
        return ResponseHandler.response(HttpStatus.OK, null, "Stock updated by color");
    }

    // 6) Update by size & color
    @PutMapping("/stock/update/bySizeAndColor")
    public ResponseEntity<CommonResponse> updateStockBySizeAndColor(@RequestParam String productName,
                                                                    @RequestParam String companyName,
                                                                    @RequestParam String category,
                                                                    @RequestParam String size,
                                                                    @RequestParam String color,
                                                                    @RequestParam Double price,
                                                                    @RequestParam Integer quantity) {
        productService.updateStockBySizeAndColor(productName, companyName, category, size, color, price, quantity);
        return ResponseHandler.response(HttpStatus.OK, null, "Stock updated by size & color");
    }

}

