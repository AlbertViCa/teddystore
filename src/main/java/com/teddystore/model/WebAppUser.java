package com.teddystore.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sun.istack.NotNull;
import com.teddystore.config.security.AuthorityDeserializer;
import com.teddystore.controller.CostumerController;
import com.teddystore.service.CostumerService;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
/**
 * @author Alberto Villalpando <br><br>
 * Clase padre de {@link Employee} y {@link Costumer}. <br><br>
 * <font color="#85ba6a"><strong>{@code @Getter, @Setter, @ToString}</strong></font>: Anotaciones de lombok; automáticamente genera los métodos correspondientes. <br><br>
 * <font color="#85ba6a"><strong>{@code @SuperBuilder}</strong></font>: Anotacion de lombok; implementa el patrón builder, <strong>super</strong> debido a que es una clase abstracta padre. <br><br>
 * <font color="#85ba6a"><strong>{@code @NoArgsConstructor, @AllArgsConstructor}</strong></font>: Anotaciones de lombok; implementa constructores sin parametros y con todos los parametros. <br><br>
 * <font color="#85ba6a"><strong>{@code @MappedSuperclass}</strong></font>: Anotación para marcar una clase padre y así hibernate entienda como persistir la información de las clases hijas en la base de datos. <br><br>
 * <font color="#85ba6a"><strong>{@code @JsonTypeInfo, @JsonSubTypes}</strong></font>: Anotaciones para que la API sepa como estructurar el JSON de las clases hijas. <br><br>
 * <font color="#85ba6a"><strong>{@code @Id, @Column, @GeneratedValue}</strong></font>: Anotaciones de hibernate que establece como se debe persistir la información en la base de datos. <br><br>
 * <font color="#85ba6a"><strong>{@code @ManyToMany}</strong></font>: Establece la cardinalidad de los objetos. Muchos usuarios pueden tener muchos privilegios y estos privilegiops pueden pertenecer a muchos usuarios. <br><br>
 * <font color="#85ba6a"><strong>{@code @Transactional}</strong></font>: Se asegura de que la información se obtenga correctamente, en caso de fallar, los cambios serán revertidos. <br><br>
 * <strong>
 * Ruta de clases: {@link com.teddystore.model.WebAppUser WebAppUser}
 *              -> {@link Costumer}
 *              -> {@link com.teddystore.repository.CostumerRepository  CostumerRepository}
 *              -> {@link CostumerService}
 *              -> {@link com.teddystore.service.CostumerServiceImp CostumerServiceImp}
 *              -> {@link CostumerController}
 * </strong>
 * @see <a href="https://projectlombok.org/">Project Lombok</a>
 * @see <a href="https://livebook.manning.com/book/java-persistence-with-hibernate-third-edition/chapter-5/v-13/">Java Persistence with Spring Data and Hibernate: 5 Mapping persistent classes</a>
 * @see <a href="https://livebook.manning.com/book/java-persistence-with-hibernate-third-edition/chapter-7/v-13/">Java Persistence with Spring Data and Hibernate: 7 Mapping inheritance</a>
 * @see <a href="https://livebook.manning.com/book/java-persistence-with-hibernate-third-edition/chapter-9/v-13/">Java Persistence with Spring Data and Hibernate: 9 Advanced entity association mappings</a>
 * @see <a href="https://livebook.manning.com/book/spring-quickly/chapter-13/">Spring Start Here: 13 Using transactions in Spring apps</a>
 * */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @Type(value = Costumer.class, name = "costumer"),
        @Type(value = Employee.class, name = "employee")
})
public abstract class WebAppUser implements UserDetails {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "User ID", example = "1", required = true)
    private Long id;

    @NotNull
    @Column(name = "FIRST_NAME", nullable = false)
    @ApiModelProperty(notes = "User full name", example = "Alberto", required = true)
    protected String firstName;

    @NotNull
    @Column(name = "LAST_NAME", nullable = false)
    @ApiModelProperty(notes = "User last name", example = "Villalpando", required = true)
    protected String lastName;

    @NotNull
    @Column(name = "SECOND_LAST_NAME")
    @ApiModelProperty(notes = "User second last name", example = "Cardona", required = true)
    protected String secondLastName;

    @NotNull
    @Column(name = "USERNAME", unique = true, nullable = false)
    @ApiModelProperty(notes = "User username", example = "Alberto", required = true)
    protected String username;

    @NotNull
    @Column(name = "PASSWORD", nullable = false)
    @ApiModelProperty(hidden = true)
    protected String password;

    @Nullable
    @Column(name = "PHONE_NUMBER", unique = true, nullable = false)
    @ApiModelProperty(notes = "User phone number", example = "123 456 7890", required = true)
    protected String phoneNumber;

    @NotNull
    @Column(name = "EMAIL", unique = true, nullable = false)
    @ApiModelProperty(notes = "User email", example = "alberto@gmail.com", required = true)
    protected String email;

    @CreationTimestamp
    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime lastUpdatedAt;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @ManyToMany(targetEntity = Authority.class, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Collection<Authority> authorities;

    @Override
    @Transactional
    @JsonDeserialize(using = AuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new LinkedList<>();
        for (Authority authority : authorities){
            grantedAuthorities.addAll(Authority.setAuthorities(String.valueOf(authority)));
        }
        return grantedAuthorities;
    }

    public <T extends WebAppUser> void updateUserDetails(T newUserDetails) {
        this.setUsername(newUserDetails.getUsername());
        this.setFirstName(newUserDetails.getFirstName());
        this.setLastName(newUserDetails.getLastName());
        this.setSecondLastName(newUserDetails.getSecondLastName());
        this.setPhoneNumber(newUserDetails.getPhoneNumber());
        this.setEmail(newUserDetails.getEmail());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
