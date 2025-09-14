package com.ekart.barasatBazar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailsDTO {
    private String detailId;
    private String size;
    private String color;
    private Double price;
    private Integer quantity;
}
