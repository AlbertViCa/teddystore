package com.teddystore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties(value = {
        "teddyOrder"
})
public class Teddy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;
    String name;
    String details;
    Double size;
    BigDecimal price;
    String imageURL;

    @ManyToMany(mappedBy = "teddies")
    @ToString.Exclude
    List<TeddyOrder> teddyOrder;
}
