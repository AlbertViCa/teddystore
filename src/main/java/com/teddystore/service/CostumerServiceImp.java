package com.teddystore.service;

import com.teddystore.exception.CostumerNotFoundException;
import com.teddystore.model.Costumer;
import com.teddystore.repository.CostumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CostumerServiceImp implements CostumerService, UserDetailsService { //TODO: IMPLEMENT COSTUMER NOT FOUND EXCEPTION WHERE NEEDED

    private final CostumerRepository costumerRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CostumerServiceImp(CostumerRepository costumerRepository, PasswordEncoder passwordEncoder) {
        this.costumerRepository = costumerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Costumer registerCostumer(Costumer costumer) {
        costumer.setPassword(passwordEncoder.encode(costumer.getPassword()));
        return costumerRepository.save(costumer);
    }

    @Override
    public Optional<Costumer> getCostumerById(Long id) {
        return Optional.ofNullable(costumerRepository.findById(id)
                .orElseThrow(() -> new CostumerNotFoundException(String.format("No costumer with id %s found", id))));
    }

    @Override
    public Optional<Costumer> getByUsername(String username) {
        return costumerRepository.getByUsername(username);
    }

    @Override
    public Iterable<Costumer> getCostumers() {
        return costumerRepository.findAll();
    }

    @Override
    public Costumer updateCostumerDetails(Long id, Costumer newCostumerDetails) {
        Costumer existingCostumer = costumerRepository.findById(id)
                .orElseThrow(() -> new CostumerNotFoundException(String.format("No costumer with id %s found", id)));

        existingCostumer.updateCostumerDetails(newCostumerDetails);

        return costumerRepository.save(existingCostumer);
    }

    @Override
    public void deleteCostumerById(Long id) {
        costumerRepository.deleteById(id);
    }

    @Override
    public void deleteCostumers() {
        costumerRepository.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Costumer> result = Optional.ofNullable(costumerRepository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("")));
        Costumer costumer = result.get();
        return new org.springframework.security.core.userdetails.User(costumer.getUsername(), costumer.getPassword(),  costumer.getAuthorities());
    }
}
