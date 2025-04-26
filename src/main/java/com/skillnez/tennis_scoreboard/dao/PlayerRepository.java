package com.skillnez.tennis_scoreboard.dao;

import com.skillnez.tennis_scoreboard.entity.Player;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.Optional;

@ApplicationScoped
public class PlayerRepository extends BaseRepository <Integer, Player>{

    @Inject
    public PlayerRepository(EntityManager entityManager) {
        super(Player.class, entityManager);
    }

    public Optional<Player> getByName(String name) {
        try {
            return Optional.ofNullable(getEntityManager()
                    .createQuery("select p from Player p where p.name = :name", Player.class)
                    .setParameter("name", name).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
