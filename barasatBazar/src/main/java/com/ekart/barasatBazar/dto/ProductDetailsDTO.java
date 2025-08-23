package com.ekart.barasatBazar.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailsDTO {
    private Long detailId;
    private String size;
    private String color;
    private Double price;
    private Integer quantity;
}
