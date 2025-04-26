package com.skillnez.tennis_scoreboard.service;

import com.skillnez.tennis_scoreboard.entity.Match;
import com.skillnez.tennis_scoreboard.entity.MatchScore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class OngoingMatchService {

    @Inject
    PlayerService playerService;

    private ConcurrentHashMap<UUID, MatchScore> ongoingMatches = new ConcurrentHashMap<>();

    public UUID createOngoingMatch (String player1, String player2) {
        Match match = Match.builder()
                .player1(playerService.takePlayer(player1))
                .player2(playerService.takePlayer(player2))
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

        //Этот uuid потом идет в ссылку, а из ссылки по нему ищется матч из мапы
    }

    private Optional<MatchScore> getOngoingMatch (UUID uuid) {
        return Optional.ofNullable(ongoingMatches.get(uuid));
    }





}
