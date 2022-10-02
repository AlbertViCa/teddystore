package com.teddystore.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Table(name = "ADDRESS")
public class DeliveryAddress {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "STREET", nullable = false)
    String street;

    @NotNull
    @Column(name = "CITY", nullable = false)
    String city;

    @NotNull
    @Column(name = "STATE", nullable = false)
    String state;

    @NotNull
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
