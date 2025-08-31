package com.ekart.barasatBazar.service;

import com.ekart.barasatBazar.dto.ProductDTO;
import com.ekart.barasatBazar.dto.ProductDetailsDTO;
import com.ekart.barasatBazar.entity.ProductDetailsEntity;
import com.ekart.barasatBazar.entity.ProductEntity;
import com.ekart.barasatBazar.repository.ProductRepository;
import com.personal.common.exception.ApplicationException;
import com.personal.common.mapper.GenericMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO createProduct(ProductDTO productDTO){
        ProductEntity product = GenericMapper.map(productDTO, ProductEntity.class);

                /*new ProductEntity();
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
        }).collect(Collectors.toList());*/

        //product.setProductDetail(detailsList);
        ProductEntity savedProduct =  productRepository.save(product);
        ProductDTO savedProductDTO = GenericMapper.map(savedProduct, ProductDTO.class);
        return savedProductDTO;
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

    @Transactional
    public ProductDetailsDTO addProductDetails(long productId, ProductDetailsDTO productDetailsDTO) {

        ProductEntity existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ApplicationException(
                        "Product not found with ID: " + productId,
                        HttpStatus.BAD_REQUEST));

        ProductDetailsEntity productDetailsEntity = new ProductDetailsEntity();
        productDetailsEntity.setSize(productDetailsDTO.getSize());
        productDetailsEntity.setColor(productDetailsDTO.getColor());
        productDetailsEntity.setPrice(productDetailsDTO.getPrice());
        productDetailsEntity.setQuantity(productDetailsDTO.getQuantity());
        productDetailsEntity.setProduct(existingProduct);
        List<ProductDetailsEntity> productDetailsList = existingProduct.getProductDetail();
        productDetailsList.add(productDetailsEntity);
        existingProduct.setProductDetail(productDetailsList);

        productRepository.save(existingProduct);

        ProductDetailsDTO savedDTO = new ProductDetailsDTO();
        savedDTO.setDetailId(productDetailsEntity.getDetailId()); // ID will be generated
        savedDTO.setSize(productDetailsEntity.getSize());
        savedDTO.setColor(productDetailsEntity.getColor());
        savedDTO.setPrice(productDetailsEntity.getPrice());
        savedDTO.setQuantity(productDetailsEntity.getQuantity());

        return savedDTO;
    }


}
