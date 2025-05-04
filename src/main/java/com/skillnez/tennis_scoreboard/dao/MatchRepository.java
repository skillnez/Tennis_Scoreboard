package com.skillnez.tennis_scoreboard.dao;

import com.skillnez.tennis_scoreboard.entity.Match;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.Cleanup;
import org.hibernate.SessionFactory;

import java.util.List;

@ApplicationScoped
public class MatchRepository extends BaseRepository<Integer, Match> {

    public MatchRepository() {
        super(Match.class);
    }

    public long countAllMatches() {
        @Cleanup var session = getSessionFactory().openSession();
        return session.createQuery("SELECT COUNT(m) FROM Match m", Long.class).getSingleResult();
    }

    public List<Match> findPagedMatches(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;

        @Cleanup var session = getSessionFactory().openSession();
        return session.createQuery("SELECT m FROM Match m ORDER BY m.id DESC", Match.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
