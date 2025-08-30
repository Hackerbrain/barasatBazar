package com.ekart.barasatBazar.service;

import com.ekart.barasatBazar.dto.ProductDTO;
import com.ekart.barasatBazar.dto.ProductDetailsDTO;
import com.ekart.barasatBazar.entity.ProductDetailsEntity;
import com.ekart.barasatBazar.entity.ProductEntity;
import com.ekart.barasatBazar.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO createProduct(ProductDTO productDTO){
        ProductEntity product = new ProductEntity();
        product.setProductName(productDTO.getProductName());
        product.setCompanyName(productDTO.getCompanyName());
        product.setCategory(productDTO.getCategory());

        List<ProductDetailsEntity> detailsList = productDTO.getProductDetail().stream().map(detailDTO -> {
            ProductDetailsEntity detail = new ProductDetailsEntity();
            detail.setSize(detailDTO.getSize());
            detail.setColor(detailDTO.getColor());
            detail.setPrice(detailDTO.getPrice());
            detail.setQuantity(detailDTO.getQuantity());
            detail.setProduct(product);
            return detail;
        }).collect(Collectors.toList());

        product.setProductDetail(detailsList);
        return mapToDTO(productRepository.save(product));
    }

    private ProductDTO mapToDTO(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setProductName(entity.getProductName());
        dto.setCompanyName(entity.getCompanyName());
        dto.setCategory(entity.getCategory());

        List<ProductDetailsDTO> detailDTOs = entity.getProductDetail().stream().map(detail -> {
            ProductDetailsDTO d = new ProductDetailsDTO();
            d.setDetailId(detail.getDetailId());
            d.setSize(detail.getSize());
            d.setColor(detail.getColor());
            d.setPrice(detail.getPrice());
            d.setQuantity(detail.getQuantity());
            return d;
        }).collect(Collectors.toList());

        dto.setProductDetail(detailDTOs);
        return dto;
    }

    public ProductDTO addProduct(long id, ProductDTO product) {
        ProductEntity existingStudent = ProductRepository.findBy(id)
                .orElseThrow(() -> new RuntimeException());
        BeanUtils.copyProperties(product, existingStudent);
        ProductEntity studentEntity = productRepository.save(existingStudent);
        return productMapper.toDto(studentEntity);
    }
}
