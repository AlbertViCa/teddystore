package com.teddystore.services;

import com.teddystore.exception.TeddyNotFoundException;
import com.teddystore.model.Teddy;
import com.teddystore.repositories.TeddyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeddyServiceImp implements TeddyService{

    private final TeddyRepository teddyRepository;

    public TeddyServiceImp(TeddyRepository teddyRepository) {
        this.teddyRepository = teddyRepository;
    }

    @Override
    public Teddy saveTeddy(Teddy teddy) {
        return teddyRepository.save(teddy);
    }

    @Override
    public Optional<Teddy> getById(Long id) {
        return Optional.ofNullable(teddyRepository.findById(id)
                .orElseThrow(() -> new TeddyNotFoundException(String.format("No teddy with id %s found", id))));
    }

    @Override
    public Optional<Teddy> getByName(String name) {
        return Optional.ofNullable(teddyRepository.findByName(name)
                .orElseThrow(() -> new TeddyNotFoundException(String.format("No teddy with name %s found", name))));
    }

    @Override
    public Optional<Iterable<Teddy>> getAllTeddies() {
        return Optional.ofNullable(Optional.of(teddyRepository.findAll())
                .orElseThrow(() -> new TeddyNotFoundException("No teddies found")));
    }

    @Override
    public void deleteTeddyById(Long id) {
        if(teddyRepository.existsById(id)) {
            teddyRepository.deleteById(id);
        } else {
            throw new TeddyNotFoundException(String.format("No teddy with id %s found", id));
        }
    }

    @Override
    public void deleteAll() {
        teddyRepository.deleteAll();
    }

}
