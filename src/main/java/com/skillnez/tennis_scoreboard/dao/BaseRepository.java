package com.skillnez.tennis_scoreboard.dao;

import com.skillnez.tennis_scoreboard.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Cleanup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Remove;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BaseRepository<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private final Class<E> clazz;
    @Getter
    private final EntityManager entityManager;

    @Override
    public E save(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(K id) {
        entityManager.remove(entityManager.find(clazz, id));
        entityManager.flush();
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @Override
    public List<E> findAll() {
        return entityManager.createQuery("FROM " + clazz.getSimpleName(), clazz).getResultList();
    }
}
