package com.skillnez.tennis_scoreboard.service;

import com.skillnez.tennis_scoreboard.entity.Match;
import com.skillnez.tennis_scoreboard.entity.MatchScore;
import com.skillnez.tennis_scoreboard.entity.Player;
import com.skillnez.tennis_scoreboard.entity.PlayerScore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class OngoingMatchService {

    @Inject
    PlayerPersistenceService playerPersistenceService;

    private final ConcurrentHashMap<UUID, MatchScore> ongoingMatches = new ConcurrentHashMap<>();

    public UUID createOngoingMatch (String player1, String player2) {
        Player playerOne = playerPersistenceService.takeOrSavePlayer(player1);
        Player playerTwo = playerPersistenceService.takeOrSavePlayer(player2);
        PlayerScore playerOneScore = PlayerScore.builder()
                .player(playerOne)
                .sets(0)
                .games(0)
                .points(0)
                .build();
        PlayerScore playerTwoScore = PlayerScore.builder()
                .player(playerTwo)
                .sets(0)
                .games(0)
                .points(0)
                .build();
        MatchScore matchScore = MatchScore.builder()
                .playerOneScore(playerOneScore)
                .playerTwoScore(playerTwoScore)
                .build();
        UUID uuid = UUID.randomUUID();
        ongoingMatches.put(uuid, matchScore);
        return uuid;
    }

    public Match getOngoingMatch (UUID uuid) {
        MatchScore matchScore = Optional.ofNullable(ongoingMatches.get(uuid))
                .orElseThrow(() -> new NotFoundException("Матч с UUID " + uuid + " не найден"));
        return Match.builder()
                .player1(matchScore.getPlayerOneScore().getPlayer())
                .player2(matchScore.getPlayerTwoScore().getPlayer())
                .matchScore(matchScore)
                .build();
    }

    public void removeOngoingMatch (UUID uuid) {
        ongoingMatches.remove(uuid);
    }

    public PlayerScore getPlayerScoreByPlayerId (int playerId, UUID uuid) {
        MatchScore matchScore = ongoingMatches.get(uuid);
        if (matchScore.getPlayerOneScore().getPlayer().getId().equals(playerId)) {
            return matchScore.getPlayerOneScore();
        }
        if (matchScore.getPlayerTwoScore().getPlayer().getId().equals(playerId)) {
            return matchScore.getPlayerTwoScore();
        } else
            throw new NotFoundException();
    }
}
