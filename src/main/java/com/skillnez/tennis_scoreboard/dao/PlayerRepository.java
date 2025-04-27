package com.skillnez.tennis_scoreboard.dao;

import com.skillnez.tennis_scoreboard.entity.Player;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;

import java.util.Optional;

@ApplicationScoped
public class PlayerRepository extends BaseRepository <Integer, Player>{

    public PlayerRepository() {
        super(Player.class);
    }

    public Optional<Player> getByName(String name) {
        try(Session session = getSessionFactory().openSession()) {
            return session.createQuery("select p from Player p where p.name = :name", Player.class)
                    .setParameter("name", name).uniqueResultOptional();
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
