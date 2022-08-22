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
public class Address {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

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
    Costumer costumerAddress;
}
