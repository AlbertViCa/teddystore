package com.teddystore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADDRESS")
public class DeliveryAddress {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "STREET", nullable = false)
    String street;

    @Column(name = "CITY", nullable = false)
    String city;

    @Column(name = "STATE", nullable = false)
    String state;

    @Column(name = "ZIP_CODE", nullable = false)
    String zipCode;

    @ManyToOne
    @JoinTable(
            name = "COSTUMMER_ADDRESS",
            joinColumns = @JoinColumn(
                    name = "ADDRESS_ID"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "COSTUMER_ID",
                    nullable = false
            )
    )
    Costumer costumer;
}
