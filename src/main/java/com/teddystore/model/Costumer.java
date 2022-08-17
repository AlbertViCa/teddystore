package com.teddystore.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Costumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "USERNAME")
    String username;

    @Column(name = "FULLNAME")
    String fullName;

    @Column(name = "PASSWORD")
    String password;

    @Column(name = "PHONE_NUMBER")
    String phoneNumber;

    @Column(name = "EMAIL")
    String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Costumer costumer = (Costumer) o;
        return id != null && Objects.equals(id, costumer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
