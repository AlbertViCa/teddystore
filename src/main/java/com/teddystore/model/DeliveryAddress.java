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
    private String street;

    @NotNull
    @Column(name = "CITY", nullable = false)
    private String city;

    @NotNull
    @Column(name = "STATE", nullable = false)
    private String state;

    @NotNull
    @Column(name = "ZIP_CODE", nullable = false)
    private String zipCode;

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
    private Costumer costumer;
}
