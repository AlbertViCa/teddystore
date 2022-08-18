package com.teddystore.services;

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

    //TODO: Make method throw 404 exception.
    @Override
    public Optional<Teddy> getById(Long id) {
        return teddyRepository.findById(id);
    }

    //TODO: Make method throw 404 exception.
    @Override
    public Optional<Teddy> getByName(String name) {
        return teddyRepository.findByName(name);
    }

    //TODO: Make method throw 404 exception.
    @Override
    public Optional<Iterable<Teddy>> getAllTeddys() {
        return Optional.of(teddyRepository.findAll());
    }

    //TODO: Make method throw 404 exception.
    @Override
    public void deleteTeddyById(Long id) {
        if(teddyRepository.existsById(id)) {
            teddyRepository.deleteById(id);
        }
    }

    @Override
    public void deleteAll() {
        teddyRepository.deleteAll();
    }

}
