package com.teddystore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teddystore.controller.CostumerController;
import com.teddystore.service.CostumerService;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
/**
 * @author Alberto Villalpando <br><br>
 * Clase hija de {@link WebAppUser}. <br><br>
 * <font color="#85ba6a"><strong>{@code @Entity}</strong></font>: Agrega la clase al <a href="https://livebook.manning.com/book/spring-quickly/chapter-2/45">Spring Context</a>. <br><br>
 * <font color="#85ba6a"><strong>{@code @JsonIgnoreProperties}</strong></font>: Establecemos los atributos que no expondremos en la API. <br><br>
 * <font color="#85ba6a"><strong>{@code @OneToMany}</strong></font>: Establece la cardinalidad de los objetos. Un cliente puden tener muchas ordenes, pero estas ordenes solo pueden pertenecer a un cliente. <br><br>
 * <strong>
 * Ruta de clases: {@link com.teddystore.model.WebAppUser WebAppUser}
 *              -> {@link Costumer}
 *              -> {@link com.teddystore.repository.CostumerRepository  CostumerRepository}
 *              -> {@link CostumerService}
 *              -> {@link com.teddystore.service.CostumerServiceImp CostumerServiceImp}
 *              -> {@link CostumerController}
 * </strong>
 * */
@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {
        //"password", //TODO: IMPLEMENT CUSTOM FILTER, SO GET METHODS DON'T RETURN PASSWORDS AND FIX TEST AFTERWARDS.
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
            mappedBy = "costumer",
            cascade = CascadeType.REMOVE
    )
    List<DeliveryAddress> addressList;

    @OneToMany(
            mappedBy = "costumer",
            cascade = CascadeType.REMOVE
    )
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
