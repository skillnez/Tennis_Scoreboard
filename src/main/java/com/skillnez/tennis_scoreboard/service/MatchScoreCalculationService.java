package com.skillnez.tennis_scoreboard.service;

import com.skillnez.tennis_scoreboard.entity.Match;
import com.skillnez.tennis_scoreboard.entity.PlayerScore;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MatchScoreCalculationService {

    private boolean isWinner(PlayerScore playerScore, int playerId) {
        return playerScore.getPlayer().getId().equals(playerId);
    }

    public void calculateScore(int playerId, Match match) {
        PlayerScore playerOneScore = match.getMatchScore().getPlayerOneScore();
        PlayerScore playerTwoScore = match.getMatchScore().getPlayerTwoScore();

        PlayerScore winner = isWinner(playerOneScore, playerId) ? playerOneScore : playerTwoScore;
        PlayerScore loser = isWinner(playerTwoScore, playerId) ? playerTwoScore : playerOneScore;

        winner.addPoints();
        if (winner.getPoints() > 3) {
            resetPoints(winner, loser);
            winner.addGames();
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

//    Просто + очки
//    if (player1.points < 41 || player2.points <41) {
//        points +15
//    }
//
//    Выйграл гейм
//    if (player1.poins > 40 || player2.points > 40 && player1.points!=40 && player2.points !=40) {
//        points = 0;
//        games +1
//    }
//
//    Ровно
//    if (player1.points = 40 && player2.points = player1.points) {
//        while (player1.points - player2.points || player2.points - player1.points < 30){
//
//        }
}
