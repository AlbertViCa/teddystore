package com.teddystore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teddystore.controller.CustomerController;
import com.teddystore.repository.CustomerRepository;
import com.teddystore.service.CustomerService;
import com.teddystore.service.CustomerServiceImp;
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
 *              -> {@link Customer}
 *              -> {@link CustomerRepository  CustomerRepository}
 *              -> {@link CustomerService}
 *              -> {@link CustomerServiceImp CustomerServiceImp}
 *              -> {@link CustomerController}
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
        //"password", //TODO: IMPLEMENT CUSTOM FILTER, SO GET METHODS DON'T RETURN PASSWORDS AND FIX TEST AFTERWARDS. DTO COULD BE A SOLUTION.
        "productOrder",
        "addressList",
        "authorities",
        "enabled",
        "accountNonExpired",
        "credentialsNonExpired",
        "accountNonLocked",
        "createdAt",
        "lastUpdatedAt",
        "version"
})
public final class Customer extends WebAppUser {

    @ToString.Exclude
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.REMOVE
    )
    private List<DeliveryAddress> addressList;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.REMOVE
    )
    @ToString.Exclude
    private List<ProductOrder> productOrder;
}
