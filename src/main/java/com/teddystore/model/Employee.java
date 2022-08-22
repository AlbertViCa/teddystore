package com.teddystore.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SecondaryTable(name = "EMPLOYEE")
public class Employee extends WebAppUser {

    @Column(name = "SALARY")
    private BigDecimal salary;
}
