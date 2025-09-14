package com.ekart.barasatBazar.service;

import com.ekart.barasatBazar.dto.ProductDTO;
import com.ekart.barasatBazar.dto.ProductDetailsDTO;
import com.ekart.barasatBazar.dto.SearchDTO;
import com.ekart.barasatBazar.entity.ProductDetailsEntity;
import com.ekart.barasatBazar.entity.ProductEntity;
import com.ekart.barasatBazar.repository.ProductRepository;
import com.ekart.barasatBazar.repository.ProductDetailsRepository;
import com.ekart.barasatBazar.utils.ProductUtils;
import com.personal.common.exception.ApplicationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductEntity product = modelMapper.map(productDTO, ProductEntity.class);
        if(!CollectionUtils.isEmpty(productDTO.getProductDetail())){
            for(ProductDetailsEntity details:product.getProductDetail()){
                details.setProduct(product);
            }
        }
        ProductEntity savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    public ProductDTO addProductDetails(String productId, ProductDetailsDTO productDetailsDTO) {
        ProductEntity existingProduct = productRepository.findById(UUID.fromString(productId))
                .orElseThrow(() -> new ApplicationException(
                        "Product not found with ID: " + productId,
                        HttpStatus.BAD_REQUEST));

        ProductDetailsEntity productDetailsEntity = modelMapper.map(productDetailsDTO, ProductDetailsEntity.class);
        productDetailsEntity.setProduct(existingProduct);

        existingProduct.getProductDetail().add(productDetailsEntity);
        ProductEntity savedProduct = productRepository.save(existingProduct);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        List<ProductDTO> allProducts = productEntityList.stream()
                .map(productEntity -> modelMapper.map(productEntity,ProductDTO.class))
                .toList();
        return allProducts;
    }

    public List<ProductDTO> searchProductDetails(SearchDTO searchParams, Pageable pageable) {

        if (SearchDTO.isNull(searchParams)) {
            return getAllProducts();
        }

        Specification<ProductEntity> specs = ProductUtils.filterProducts(searchParams);
        Page<ProductEntity> pageDetails = productRepository.findAll(specs, pageable);
        List<ProductEntity> productEntityList = pageDetails.getContent();
        List<ProductDTO> productList = productEntityList.stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDTO.class))
                .toList();

        return ProductUtils.filterProductDetails(productList, searchParams);
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {

        if(Objects.isNull(productDTO.getId())){
            throw new ApplicationException("Product Id is missing",HttpStatus.BAD_REQUEST);
        }

        ProductEntity existingProduct = productRepository.findById(UUID.fromString(productDTO.getId()))
                .orElseThrow(()->new ApplicationException("Product does not exist",HttpStatus.NOT_FOUND));

        ProductUtils.updateProduct(existingProduct,productDTO);
        if(!CollectionUtils.isEmpty(productDTO.getProductDetail())){
            productDTO.getProductDetail().forEach(productDetailsDTO -> {
                if(Objects.isNull(productDetailsDTO.getDetailId())){
                    throw new ApplicationException("ProductDetail Id is missing",HttpStatus.BAD_REQUEST);
                }
                ProductDetailsEntity existingProductDetail = existingProduct.getProductDetail()
                        .stream()
                        .filter(productDetailsEntity -> productDetailsEntity.getDetailId().equals(UUID.fromString(productDetailsDTO.getDetailId())))
                        .findFirst().orElseThrow(()->new ApplicationException("ProductDetail does not exist",HttpStatus.NOT_FOUND));

                ProductUtils.updateProductDetails(existingProductDetail,productDetailsDTO);
            });
        }
        ProductEntity savedProduct = productRepository.save(existingProduct);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }
}


