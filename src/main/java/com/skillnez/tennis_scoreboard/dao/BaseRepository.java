package com.skillnez.tennis_scoreboard.dao;

import com.skillnez.tennis_scoreboard.entity.BaseEntity;
import jakarta.inject.Inject;
import lombok.*;
import org.hibernate.Remove;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseRepository<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private final Class<E> clazz;

    @Getter
    @Inject
    private SessionFactory sessionFactory;

    @Override
    public E save(E entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        return entity;
    }

    @Override
    public void delete(K id) {
        @Cleanup var session = sessionFactory.openSession();
        session.remove(session.get(clazz, id));
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void update(E entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.merge(entity);
    }

    @Override
    public Optional<E> findById(K id) {
        @Cleanup var session = sessionFactory.openSession();
        return Optional.ofNullable(session.get(clazz, id));
    }

    @Override
    public List<E> findAll() {
        @Cleanup var session = sessionFactory.openSession();
        return session.createQuery("FROM " + clazz.getSimpleName(), clazz).getResultList();
    }
}
