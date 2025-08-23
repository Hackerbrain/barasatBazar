package com.ekart.barasatBazar.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="product",
        uniqueConstraints = {
            @UniqueConstraint(
                    columnNames = {"productName", "companyName", "category"}
            )
        })
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String productName;

    @Column(nullable=false)
    private String companyName;

    // One-to-One relationship with ProductDetail
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDetailsEntity> productDetail;

    @Column(nullable = false)
    private String category;
}
