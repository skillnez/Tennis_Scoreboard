package com.skillnez.tennis_scoreboard.dao;

import com.skillnez.tennis_scoreboard.entity.Match;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class MatchRepository extends BaseRepository<Integer, Match> {

    @Inject
    public MatchRepository(EntityManager entityManager) {
        super(Match.class, entityManager);
    }
}
