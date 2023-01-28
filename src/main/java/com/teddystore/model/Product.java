package com.teddystore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties(value = {
        "productOrder"
})
public final class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Nullable
    @Column(name = "DETAILS")
    private String details;

    @NotNull
    @Column(name = "SIZE", nullable = false)
    private Double size;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Nullable
    @Column(name = "IMAGE_URL")
    private String imageURL;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private Category category;

    @CreationTimestamp
    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime lastUpdatedAt;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    private List<ProductOrder> productOrder;

    public enum Category{
        TEDDY,
        PAINT,
        FIGURE,
    }

    public void updateTeddyDetails(Product newProductDetails) {
        this.setName(newProductDetails.getName());
        this.setDetails(newProductDetails.getDetails());
        this.setSize(newProductDetails.getSize());
        this.setPrice(newProductDetails.getPrice());
        this.setImageURL(newProductDetails.getImageURL());
    }
}
