package com.givts.app.service;

import com.givts.app.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class CrudService<T> {

    JpaRepository<T, Long> repository;

    public CrudService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public T create(T obj) {
        return repository.save(obj);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(long id) {
        Optional<T> repoObj = repository.findById(id);
        return repoObj.orElse(null);
    }
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public T update(T obj) {
        return repository.save(obj);
    }
}
