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

    public long countFilteredMatches(String playerName) {
            @Cleanup var session = getSessionFactory().openSession();
            return session.createQuery("SELECT COUNT(m) FROM Match m " +
                                       "WHERE LOWER(m.player1.name) like :playerName " +
                                       "OR LOWER(m.player2.name) like :playerName", Long.class)
                    .setParameter("playerName", "%" + playerName.toLowerCase() + "%")
                    .getSingleResult();
    }

    public List<Match> findPagedMatches(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;

        @Cleanup var session = getSessionFactory().openSession();
        return session.createQuery("SELECT m FROM Match m ORDER BY m.id DESC", Match.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Match> findPagedMatchesByPlayerName(int pageNumber, int pageSize, String playerName) {
        int offset = (pageNumber - 1) * pageSize;

        @Cleanup var session = getSessionFactory().openSession();
        return session.createQuery("SELECT m FROM Match m " +
                                   "WHERE LOWER(m.player1.name) like :playerName " +
                                   "OR LOWER(m.player2.name) like :playerName " +
                                   "ORDER BY m.id DESC", Match.class)
                .setParameter("playerName", "%" + playerName.toLowerCase() + "%")
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
