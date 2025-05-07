package com.skillnez.tennis_scoreboard.service;

import com.skillnez.tennis_scoreboard.entity.Match;
import com.skillnez.tennis_scoreboard.entity.PlayerScore;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApplicationScoped
public class MatchScoreCalculationService {

    private boolean tieBreak = false;
    private boolean isMatchEnded = false;

    private boolean isWinner(PlayerScore playerScore, int playerId) {
        return playerScore.getPlayer().getId().equals(playerId);
    }

    public boolean isMatchEnded() {
        return isMatchEnded;
    }

    private void endMatch() {
        isMatchEnded = true;
    }

    public void startMatch() {
        isMatchEnded = false;
    }

    public void calculateScore(int playerId, Match match) {
        PlayerScore playerOneScore = match.getMatchScore().getPlayerOneScore();
        PlayerScore playerTwoScore = match.getMatchScore().getPlayerTwoScore();

        PlayerScore winner = isWinner(playerOneScore, playerId) ? playerOneScore : playerTwoScore;
        PlayerScore loser = !isWinner(playerTwoScore, playerId) ? playerTwoScore : playerOneScore;

        if (tieBreak && !isMatchEnded) {
            winner.setPoints(winner.getPoints() + 1);
            if (winner.getPoints() >= 7 && winner.getPoints() - loser.getPoints() >= 2) {
                winner.setSets(winner.getSets() + 1);
                resetGames(winner, loser);
                tieBreak = false;
                resetPoints(winner, loser);
            }
            return;
        }

        if (winner.getPoints() < 3 && !tieBreak && !isMatchEnded) {
            winner.setPoints(winner.getPoints() + 1);
        } else if (winner.getPoints() == 3 && !tieBreak && !isMatchEnded) {
            if (loser.getPoints() < 3) {
                winGame(match, winner, loser);
            } else if (loser.getPoints() == 3) {
                winner.setPoints(4);
            } else {
                loser.setPoints(3);
            }
        } else if (winner.getPoints() == 4 && !tieBreak && !isMatchEnded) {
            winGame(match, winner, loser);
        }

        if (winner.getSets() == 2) {
            endMatch();
            match.setWinner(winner.getPlayer());
        }
    }

    private void winGame(Match match, PlayerScore winner, PlayerScore loser) {
        winner.setGames(winner.getGames() + 1);
        resetPoints(winner, loser);

        int playerOneGames = match.getMatchScore().getPlayerOneScore().getGames();
        int playerTwoGames = match.getMatchScore().getPlayerTwoScore().getGames();

        if (playerOneGames == 6 && playerTwoGames == 6) {
            tieBreak = true;
        } else if ((playerOneGames >= 6 || playerTwoGames >= 6) && Math.abs(playerOneGames - playerTwoGames) >= 2) {
            if (playerOneGames > playerTwoGames) {
                match.getMatchScore().getPlayerOneScore().setSets(match.getMatchScore().getPlayerOneScore().getSets() + 1);
            } else {
                match.getMatchScore().getPlayerTwoScore().setSets(match.getMatchScore().getPlayerTwoScore().getSets() + 1);
            }
            resetGames(match.getMatchScore().getPlayerOneScore(), match.getMatchScore().getPlayerTwoScore());
        }
    }

    private void resetPoints(PlayerScore playerOneScore, PlayerScore playerTwoScore) {
        playerOneScore.setPoints(0);
        playerTwoScore.setPoints(0);
    }

    private void resetGames(PlayerScore playerOneScore, PlayerScore playerTwoScore) {
        playerOneScore.setGames(0);
        playerTwoScore.setGames(0);
    }

    public String formatPoints (PlayerScore playerScore) {
        if (tieBreak) {
            return String.valueOf(playerScore.getPoints()); // просто число
        }
        return switch (playerScore.getPoints()) {
            case 0 -> "0";
            case 1 -> "15";
            case 2 -> "30";
            case 3 -> "40";
            case 4 -> "AD";
            default -> "";
        };
    }

}
