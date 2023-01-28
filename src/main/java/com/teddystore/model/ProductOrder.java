package com.teddystore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {
        "customer",
        "creditCardNumber",
        "creditCardExpirationDate",
        "creditCardCVV"
})
@Embeddable
@Table(name = "PRODUCT_ORDER")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @CreationTimestamp
    private LocalDateTime placedAt;

    private String deliveryStreet;

    private String deliveryCity;

    private String deliveryState;

    private String zip;

    private String creditCardNumber;

    private String creditCardExpirationDate;

    private String creditCardCVV;

    @NotNull
    @Column(name = "TOTAL_COST", nullable = false)
    private BigDecimal totalCost;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "CUSTOMER_ID")
    @ToString.Exclude
    private Customer customer;

    @ManyToMany(targetEntity = Product.class)
    @ToString.Exclude
    @JoinColumn(name = "PRODUCT_ID")
    private List<Product> products;
}
