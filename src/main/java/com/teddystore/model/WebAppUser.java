package com.teddystore.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.teddystore.config.security.AuthorityDeserializer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@JsonDeserialize(as = Costumer.class)
public abstract class WebAppUser implements UserDetails {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FULL_NAME", nullable = false)
    protected String fullName;

    @Column(name = "USERNAME", unique = true, nullable = false)
    protected String username;

    @Column(name = "PASSWORD", nullable = false)
    protected String password;

    @Column(name = "PHONE_NUMBER", unique = true, nullable = false)
    protected String phoneNumber;

    @Column(name = "EMAIL", unique = true, nullable = false)
    protected String email;

    private String authority;

    @Override
    @JsonDeserialize(using = AuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> this.authority);
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
