package com.ekart.barasatBazar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchDTO {

    private String priceRange;
    private String category;
    private String companyName;
    private String productName;
    private String size;
    private String color;

    public static boolean isNull(SearchDTO searchDTO) {
        return Objects.isNull(searchDTO.priceRange) && Objects.isNull(searchDTO.category) && Objects.isNull(searchDTO.companyName)
        && Objects.isNull(searchDTO.productName) && Objects.isNull(searchDTO.size) && Objects.isNull(searchDTO.color);
    }

    public static List<Double> getPriceRange(String priceRange){
        String[] parts = priceRange.split("-");
        double min = Double.parseDouble(parts[0]);
        double max = Double.parseDouble(parts[1]);
        return List.of(min,max);
    }
}
