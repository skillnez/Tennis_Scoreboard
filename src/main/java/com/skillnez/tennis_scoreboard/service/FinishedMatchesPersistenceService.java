package com.skillnez.tennis_scoreboard.service;

import com.skillnez.tennis_scoreboard.dao.MatchRepository;
import com.skillnez.tennis_scoreboard.dao.PlayerRepository;
import com.skillnez.tennis_scoreboard.entity.Match;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class FinishedMatchesPersistenceService {

    @Inject
    MatchRepository matchRepository;

    public Match save(Match match) {
        return matchRepository.save(match);
    }



}
