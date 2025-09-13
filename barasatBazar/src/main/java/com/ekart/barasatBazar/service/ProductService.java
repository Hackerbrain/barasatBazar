package com.ekart.barasatBazar.service;

import com.ekart.barasatBazar.dto.ProductDTO;
import com.ekart.barasatBazar.dto.ProductDetailsDTO;
import com.ekart.barasatBazar.entity.ProductDetailsEntity;
import com.ekart.barasatBazar.entity.ProductEntity;
import com.ekart.barasatBazar.repository.ProductRepository;
import com.ekart.barasatBazar.repository.ProductDetailsRepository;
import com.personal.common.exception.ApplicationException;
import com.personal.common.mapper.GenericMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductEntity product = GenericMapper.map(productDTO, ProductEntity.class);
        ProductEntity savedProduct = productRepository.save(product);
        return GenericMapper.map(savedProduct, ProductDTO.class);
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

        existingProduct.getProductDetail().add(productDetailsEntity);
        productRepository.save(existingProduct);

        ProductDetailsDTO savedDTO = new ProductDetailsDTO();
        savedDTO.setDetailId(productDetailsEntity.getDetailId());
        savedDTO.setSize(productDetailsEntity.getSize());
        savedDTO.setColor(productDetailsEntity.getColor());
        savedDTO.setPrice(productDetailsEntity.getPrice());
        savedDTO.setQuantity(productDetailsEntity.getQuantity());

        return savedDTO;
    }

    //Fetch method

    public List<ProductDetailsDTO> getByBasic(String productName, String companyName, String category) {
        return productDetailsRepository.findByBasicFilters(productName, companyName, category)
                .stream().map(this::mapToDetailsDTO).collect(Collectors.toList());
    }

    public List<ProductDetailsDTO> getBySize(String productName, String companyName, String category, String size) {
        return productDetailsRepository.findByWithSize(productName, companyName, category, size)
                .stream().map(this::mapToDetailsDTO).collect(Collectors.toList());
    }

    public List<ProductDetailsDTO> getByColor(String productName, String companyName, String category, String color) {
        return productDetailsRepository.findByWithColor(productName, companyName, category, color)
                .stream().map(this::mapToDetailsDTO).collect(Collectors.toList());
    }

    public List<ProductDetailsDTO> getBySizeAndPrice(String productName, String companyName, String category, String size, Double price) {
        return productDetailsRepository.findByWithSizeAndPrice(productName, companyName, category, size, price)
                .stream().map(this::mapToDetailsDTO).collect(Collectors.toList());
    }

    public List<ProductDetailsDTO> getByColorAndPrice(String productName, String companyName, String category, String color, Double price) {
        return productDetailsRepository.findByWithColorAndPrice(productName, companyName, category, color, price)
                .stream().map(this::mapToDetailsDTO).collect(Collectors.toList());
    }

    // 🔽 Utility mapper (Entity -> DTO)
    private ProductDetailsDTO mapToDetailsDTO(ProductDetailsEntity entity) {
        ProductDetailsDTO dto = new ProductDetailsDTO();
        dto.setDetailId(entity.getDetailId());
        dto.setSize(entity.getSize());
        dto.setColor(entity.getColor());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());
        return dto;
    }


    //update

    @Transactional
    public void updateStockByProductId(Long productId, ProductDetailsDTO dto) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ApplicationException("Product not found with id: " + productId, HttpStatus.NOT_FOUND));

        ProductDetailsEntity newDetail = new ProductDetailsEntity();
        newDetail.setProduct(product);
        newDetail.setSize(dto.getSize());
        newDetail.setColor(dto.getColor());
        newDetail.setPrice(dto.getPrice());
        newDetail.setQuantity(dto.getQuantity());

        product.getProductDetail().add(newDetail);
        productRepository.save(product);
    }

    @Transactional
    public void updateStockByProductIdAndDetailId(Long productId, Long detailId, ProductDetailsDTO dto) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ApplicationException("Product not found", HttpStatus.NOT_FOUND));

        ProductDetailsEntity detail = productDetailsRepository.findById(detailId)
                .orElseThrow(() -> new ApplicationException("Product detail not found", HttpStatus.NOT_FOUND));

        if (!detail.getProduct().getId().equals(productId)) {
            throw new ApplicationException("Detail does not belong to product", HttpStatus.BAD_REQUEST);
        }

        detail.setSize(dto.getSize());
        detail.setColor(dto.getColor());
        detail.setPrice(dto.getPrice());
        detail.setQuantity(dto.getQuantity());
        productDetailsRepository.save(detail);
    }

    @Transactional
    public void updateStockByBasic(String productName, String companyName, String category, ProductDetailsDTO dto) {
        List<ProductDetailsEntity> details = productDetailsRepository.findByBasicFilters(productName, companyName, category);

        if (details.isEmpty()) {
            // create new productDetails
            ProductEntity product = productRepository.findAll().stream()
                    .filter(p -> p.getProductName().equals(productName)
                            && p.getCompanyName().equals(companyName)
                            && p.getCategory().equals(category))
                    .findFirst()
                    .orElseThrow(() -> new ApplicationException("Product not found with given info", HttpStatus.NOT_FOUND));

            ProductDetailsEntity newDetail = new ProductDetailsEntity();
            newDetail.setProduct(product);
            newDetail.setSize(dto.getSize());
            newDetail.setColor(dto.getColor());
            newDetail.setPrice(dto.getPrice());
            newDetail.setQuantity(dto.getQuantity());
            product.getProductDetail().add(newDetail);
            productRepository.save(product);
        } else {
            // update first matched detail
            ProductDetailsEntity detail = details.get(0);
            detail.setSize(dto.getSize());
            detail.setColor(dto.getColor());
            detail.setPrice(dto.getPrice());
            detail.setQuantity(dto.getQuantity());
            productDetailsRepository.save(detail);
        }
    }

    @Transactional
    public void updateStockBySize(String productName, String companyName, String category, String size, ProductDetailsDTO dto) {
        List<ProductDetailsEntity> details = productDetailsRepository.findByWithSize(productName, companyName, category, size);

        if (details.isEmpty()) {
            // create new
            updateStockByBasic(productName, companyName, category, dto);
        } else {
            ProductDetailsEntity detail = details.get(0);
            detail.setColor(dto.getColor());
            detail.setPrice(dto.getPrice());
            detail.setQuantity(dto.getQuantity());
            productDetailsRepository.save(detail);
        }
    }

    @Transactional
    public void updateStockByColor(String productName, String companyName, String category, String color, ProductDetailsDTO dto) {
        List<ProductDetailsEntity> details = productDetailsRepository.findByWithColor(productName, companyName, category, color);

        if (details.isEmpty()) {
            // create new
            updateStockByBasic(productName, companyName, category, dto);
        } else {
            ProductDetailsEntity detail = details.get(0);
            detail.setSize(dto.getSize());
            detail.setPrice(dto.getPrice());
            detail.setQuantity(dto.getQuantity());
            productDetailsRepository.save(detail);
        }
    }

    @Transactional
    public void updateStockBySizeAndColor(String productName, String companyName, String category, String size, String color, Double price, Integer quantity) {
        List<ProductDetailsEntity> details = productDetailsRepository.findByWithSize(productName, companyName, category, size)
                .stream().filter(d -> d.getColor().equals(color)).collect(Collectors.toList());

        if (details.isEmpty()) {
            throw new ApplicationException("No product detail found with given size & color", HttpStatus.NOT_FOUND);
        }

        ProductDetailsEntity detail = details.get(0);
        detail.setPrice(price);
        detail.setQuantity(quantity);
        productDetailsRepository.save(detail);
    }

}


