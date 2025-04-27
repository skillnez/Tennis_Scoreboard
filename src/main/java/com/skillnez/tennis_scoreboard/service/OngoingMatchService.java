package com.skillnez.tennis_scoreboard.service;

import com.skillnez.tennis_scoreboard.entity.Match;
import com.skillnez.tennis_scoreboard.entity.MatchScore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class OngoingMatchService {

    @Inject
    PlayerService playerService;

    private final ConcurrentHashMap<UUID, MatchScore> ongoingMatches = new ConcurrentHashMap<>();

    public UUID createOngoingMatch (String player1, String player2) {
        Match match = Match.builder()
                .player1(playerService.takeOrSavePlayer(player1))
                .player2(playerService.takeOrSavePlayer(player2))
                .build();
        MatchScore matchScore = MatchScore.builder()
                .match(match)
                .sets(MatchScore.INIT_SET_VALUE)
                .games(MatchScore.INIT_GAME_VALUE)
                .points(MatchScore.INIT_POINTS_VALUE)
                .build();
        UUID uuid = UUID.randomUUID();
        ongoingMatches.put(uuid, matchScore);
        return uuid;

    }
    public Optional<MatchScore> getOngoingMatch (UUID uuid) {
        return Optional.ofNullable(ongoingMatches.get(uuid));
    }
}
