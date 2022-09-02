package com.teddystore.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    public static List<? extends GrantedAuthority> setAuthorities(String authorities) {
        return AuthorityUtils.createAuthorityList(authorities);
    }

    @Override
    public String toString() {
        return id;
    }
}
