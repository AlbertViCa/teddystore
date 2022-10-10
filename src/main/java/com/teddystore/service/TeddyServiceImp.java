package com.teddystore.service;

import com.teddystore.exception.TeddyNotFoundException;
import com.teddystore.model.Teddy;
import com.teddystore.repository.TeddyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TeddyServiceImp implements TeddyService {

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
    public Teddy updateTeddyDetails(Long id, Teddy teddy) {
        Teddy existingTeddy = teddyRepository.findById(id)
                .orElseThrow(() -> new TeddyNotFoundException(String.format("No teddy with id %s found", id)));

        existingTeddy.setName(teddy.getName());
        existingTeddy.setDetails(teddy.getDetails());
        existingTeddy.setSize(teddy.getSize());
        existingTeddy.setPrice(teddy.getPrice());
        existingTeddy.setImageURL(teddy.getImageURL());

        return teddyRepository.save(existingTeddy);
    }

    @Override
    public void deleteTeddyById(Long id) {
        if(teddyExists(id)) teddyRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        teddyRepository.deleteAll();
    }

    private boolean teddyExists(Long id) {
        if(teddyRepository.existsById(id)) {
            return true;
        } else {
            throw new TeddyNotFoundException(String.format("No teddy with id %s found", id));
        }
    }
}
