package com.teddystore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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
        "costumer",
        "creditCardNumber",
        "creditCardExpirationDate",
        "creditCardCVV"
})
@Table(name = "TEDDY_ORDER")
public class TeddyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;
    LocalDateTime placedAt;
    String deliveryStreet;
    String deliveryCity;
    String deliveryState;
    String zip;
    String creditCardNumber;
    String creditCardExpirationDate;
    String creditCardCVV;
    BigDecimal totalCost;

    @ManyToOne(targetEntity = Costumer.class)
    @JoinColumn(name = "COSTUMER_ID")
    @ToString.Exclude
    Costumer costumer;

    @ManyToMany(targetEntity = Teddy.class)
    @ToString.Exclude
    @JoinColumn(name = "TEDDY_ID")
    List<Teddy> teddies;
}
