package com.ekart.barasatBazar.utils;

import com.ekart.barasatBazar.dto.ProductDTO;
import com.ekart.barasatBazar.dto.ProductDetailsDTO;
import com.ekart.barasatBazar.dto.SearchDTO;
import com.ekart.barasatBazar.entity.ProductDetailsEntity;
import com.ekart.barasatBazar.entity.ProductEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductUtils {

    public static Specification<ProductEntity> filterProducts(SearchDTO searchParams) {
        return (root,query,cb)-> {

            Double minRange = null;
            Double maxRange = null;
            if(Objects.nonNull(searchParams.getPriceRange())){
                List<Double> ranges = SearchDTO.getPriceRange(searchParams.getPriceRange());
                minRange = ranges.get(0);
                maxRange = ranges.get(1);
            }
            List<Predicate> predicates = new ArrayList<>();

            if(Objects.nonNull(searchParams.getProductName())){
                predicates.add(cb.equal(cb.lower(root.get("productName")), searchParams.getProductName().toLowerCase()));
            }

            if(Objects.nonNull(searchParams.getCompanyName())){
                predicates.add(cb.equal(cb.lower(root.get("companyName")), searchParams.getCompanyName().toLowerCase()));
            }

            if(Objects.nonNull(searchParams.getCategory())){
                predicates.add(cb.equal(cb.lower(root.get("category")), searchParams.getCategory().toLowerCase()));
            }

            Join<Object,Object> details = root.join("productDetail", JoinType.LEFT);

            if(Objects.nonNull(searchParams.getSize())){
                predicates.add(cb.equal(cb.lower(details.get("size")), searchParams.getSize().toLowerCase()));
            }

            if(Objects.nonNull(searchParams.getColor())){
                predicates.add(cb.equal(cb.lower(details.get("color")), searchParams.getColor().toLowerCase()));
            }

            if(Objects.nonNull(minRange)){
                predicates.add(cb.greaterThanOrEqualTo(details.get("price"), minRange));
            }

            if(Objects.nonNull(maxRange)){
                predicates.add(cb.lessThanOrEqualTo(details.get("price"), maxRange));
            }
            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static List<ProductDTO> filterProductDetails(List<ProductDTO> searchedProducts, SearchDTO searchDetails){
        searchedProducts.forEach(product -> {
            List<ProductDetailsDTO> productDetails =product.getProductDetail().stream()
                    .filter(productDetailsDTO -> {
                        if(Objects.isNull(searchDetails.getColor()) && Objects.isNull(searchDetails.getSize()) && Objects.isNull(searchDetails.getPriceRange())){
                            return true;
                        }

                        boolean matches = false;
                        if(Objects.nonNull(searchDetails.getColor())){
                            matches = productDetailsDTO.getColor().equalsIgnoreCase(searchDetails.getColor());
                        }

                        if(Objects.nonNull(searchDetails.getSize())){
                            matches = matches || productDetailsDTO.getSize().equalsIgnoreCase(searchDetails.getSize());
                        }

                        if(Objects.nonNull(searchDetails.getPriceRange())){
                            List<Double> range = SearchDTO.getPriceRange(searchDetails.getPriceRange());
                            matches = matches || (productDetailsDTO.getPrice()>=range.get(0) && productDetailsDTO.getPrice()<=range.get(1));
                        }
                        return matches;
                    }).toList();
            product.setProductDetail(productDetails);
        });
        return searchedProducts;
    }

    public static void updateProduct(ProductEntity existingProduct,ProductDTO newProduct){
        if(Objects.nonNull(newProduct.getCategory())){
            existingProduct.setCategory(newProduct.getCategory());
        }

        if(Objects.nonNull(newProduct.getProductName())){
            existingProduct.setProductName(newProduct.getProductName());
        }

        if(Objects.nonNull(newProduct.getCompanyName())){
            existingProduct.setCompanyName(newProduct.getCompanyName());
        }
    }

    public static void updateProductDetails(ProductDetailsEntity existingDetails, ProductDetailsDTO newDetails){
        if(Objects.nonNull(newDetails.getQuantity())){
            existingDetails.setQuantity(newDetails.getQuantity());
        }

        if(Objects.nonNull(newDetails.getColor())){
            existingDetails.setColor(newDetails.getColor());
        }

        if(Objects.nonNull(newDetails.getSize())){
            existingDetails.setSize(newDetails.getSize());
        }

        if(Objects.nonNull(newDetails.getPrice())){
            existingDetails.setPrice(newDetails.getPrice());
        }
    }
}
