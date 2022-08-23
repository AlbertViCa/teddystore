package com.teddystore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SecondaryTable(name = "COSTUMER")
public class Costumer extends WebAppUser {

    @ToString.Exclude
    @OneToMany(
            mappedBy = "costumerAddress",
            cascade = CascadeType.REMOVE
    )
    List<Address> addressList;
}
