package com.teddystore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {
        //"password",
        "teddyOrder",
        "addressList",
        "authorities",
        "enabled",
        "accountNonExpired",
        "credentialsNonExpired",
        "accountNonLocked"
})
public class Costumer extends WebAppUser {

    @ToString.Exclude
    @OneToMany(
            mappedBy = "costumerAddress",
            cascade = CascadeType.REMOVE
    )
    List<Address> addressList;

    @OneToMany(mappedBy = "costumer")
    @ToString.Exclude
    List<TeddyOrder> teddyOrder;

    public void updateCostumerDetails(Costumer newCostumerDetails) {
        this.setUsername(newCostumerDetails.getUsername());
        this.setFullName(newCostumerDetails.getFullName());
        this.setPassword(newCostumerDetails.getPassword());
        this.setPhoneNumber(newCostumerDetails.getPhoneNumber());
        this.setEmail(newCostumerDetails.getEmail());
    }
}
