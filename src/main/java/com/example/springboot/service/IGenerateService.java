package com.example.springboot.service;

import java.util.Optional;

public interface IGenerateService<E> {
    Iterable<E> findAll();

    E save(E e);

    E update(E e, Long id);

    void delete(Long id);

    Optional<E> findOne(Long id);
}
