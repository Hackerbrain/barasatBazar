package com.ekart.barasatBazar.dto;

import lombok.*;

import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String productName;
    private String companyName;
    private String category;
    private List<ProductDetailsDTO> productDetail;
}
