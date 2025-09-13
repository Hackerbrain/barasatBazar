package com.ekart.barasatBazar.repository;

import com.ekart.barasatBazar.entity.ProductDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDetailsRepository extends JpaRepository<ProductDetailsEntity, Long> {

    // 1) By productName, companyName, category
    @Query("SELECT pd FROM ProductDetailsEntity pd " +
            "JOIN pd.product p " +
            "WHERE p.productName = :productName " +
            "AND p.companyName = :companyName " +
            "AND p.category = :category")
    List<ProductDetailsEntity> findByBasicFilters(@Param("productName") String productName,
                                                  @Param("companyName") String companyName,
                                                  @Param("category") String category);

    // 2) By productName, companyName, category, size
    @Query("SELECT pd FROM ProductDetailsEntity pd " +
            "JOIN pd.product p " +
            "WHERE p.productName = :productName " +
            "AND p.companyName = :companyName " +
            "AND p.category = :category " +
            "AND pd.size = :size")
    List<ProductDetailsEntity> findByWithSize(@Param("productName") String productName,
                                              @Param("companyName") String companyName,
                                              @Param("category") String category,
                                              @Param("size") String size);

    // 3) By productName, companyName, category, color
    @Query("SELECT pd FROM ProductDetailsEntity pd " +
            "JOIN pd.product p " +
            "WHERE p.productName = :productName " +
            "AND p.companyName = :companyName " +
            "AND p.category = :category " +
            "AND pd.color = :color")
    List<ProductDetailsEntity> findByWithColor(@Param("productName") String productName,
                                               @Param("companyName") String companyName,
                                               @Param("category") String category,
                                               @Param("color") String color);

    // 4) By productName, companyName, category, size, price (<= price)
    @Query("SELECT pd FROM ProductDetailsEntity pd " +
            "JOIN pd.product p " +
            "WHERE p.productName = :productName " +
            "AND p.companyName = :companyName " +
            "AND p.category = :category " +
            "AND pd.size = :size " +
            "AND pd.price <= :price")
    List<ProductDetailsEntity> findByWithSizeAndPrice(@Param("productName") String productName,
                                                      @Param("companyName") String companyName,
                                                      @Param("category") String category,
                                                      @Param("size") String size,
                                                      @Param("price") Double price);

    // 5) By productName, companyName, category, color, price (<= price)
    @Query("SELECT pd FROM ProductDetailsEntity pd " +
            "JOIN pd.product p " +
            "WHERE p.productName = :productName " +
            "AND p.companyName = :companyName " +
            "AND p.category = :category " +
            "AND pd.color = :color " +
            "AND pd.price <= :price")
    List<ProductDetailsEntity> findByWithColorAndPrice(@Param("productName") String productName,
                                                       @Param("companyName") String companyName,
                                                       @Param("category") String category,
                                                       @Param("color") String color,
                                                       @Param("price") Double price);
}

