package br.com.sistema.academico.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    void save(T entity);
    void update(T entity);
    void delete(T entity);
    Optional<T> findById(Long id);
    List<T> findAll();
}
