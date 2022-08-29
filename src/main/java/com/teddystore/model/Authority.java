package com.teddystore.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "AUTHORITY", nullable = false, unique = true)
    private String name;

    public List<? extends GrantedAuthority> setAuthorities(List<String> authoritiesList) {
        return AuthorityUtils.createAuthorityList();
    }
}
