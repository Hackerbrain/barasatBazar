package com.ekart.barasatBazar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Product Information")
public class ProductDTO {

    @Schema(
            description = "Unique Identifier Of The Product",
            example = "1"
    )
    private String id;

    @Schema(
            description = "Name Of The Product",
            example = "Ticket"
    )
    private String productName;

    @Schema(
            description = "Company Name which has created this product",
            example = "Axe"
    )
    private String companyName;

    @Schema(
            description = "Category of The Product",
            example = "Perfume"
    )
    private String category;
    @Schema(
            description = "Unique Identifier Of The Product",
            example = "1"
    )
    private List<ProductDetailsDTO> productDetail;
}
