package com.teddystore.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.teddystore.config.security.AuthorityDeserializer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;
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

    @ManyToMany(targetEntity = Authority.class, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Authority> authorities;

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
