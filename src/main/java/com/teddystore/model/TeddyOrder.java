package com.teddystore.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class TeddyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;
    Date placedAt;
    Costumer costumer;
    String deliveryStreet;
    String deliveryCity;
    String deliveryState;
    String zip;
    String creditCardNumber;
    String creditCardExpirationDate;
    String creditCardCVV;
    BigDecimal totalCost;
    List<Teddy> teddys;
}
