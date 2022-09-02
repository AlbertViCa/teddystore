package com.teddystore.service;

import com.teddystore.model.Authority;

import java.util.List;

public interface AuthorityService {
    List<Authority> findAll();

    void saveAuthority(Authority authority);
}
