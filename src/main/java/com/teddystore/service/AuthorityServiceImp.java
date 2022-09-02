package com.teddystore.service;

import com.teddystore.model.Authority;
import com.teddystore.repository.AuthorityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorityServiceImp implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImp(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<Authority> findAll() {
        return (List<Authority>) authorityRepository.findAll();
    }

    @Override
    public void saveAuthority(Authority authority) {
        authorityRepository.save(authority);
    }
}
