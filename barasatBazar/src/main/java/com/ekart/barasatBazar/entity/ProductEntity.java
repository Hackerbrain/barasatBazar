package com.ekart.barasatBazar.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

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
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

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
