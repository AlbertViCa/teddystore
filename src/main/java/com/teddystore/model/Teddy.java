package com.teddystore.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

@Data
@Builder
public class Teddy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;
    String name;
    String details;
    Double size;
    BigDecimal price;
    BufferedImage image;
}
