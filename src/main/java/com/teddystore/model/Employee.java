package com.teddystore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends WebAppUser {

    @Column(name = "SALARY")
    private BigDecimal salary;
}
