package com.skillnez.tennis_scoreboard.controller;

import com.skillnez.tennis_scoreboard.dao.PlayerRepository;
import com.skillnez.tennis_scoreboard.entity.Match;
import com.skillnez.tennis_scoreboard.entity.Player;
import com.skillnez.tennis_scoreboard.entity.PlayerScore;
import com.skillnez.tennis_scoreboard.service.FinishedMatchesPersistenceService;
import com.skillnez.tennis_scoreboard.service.MatchScoreCalculationService;
import com.skillnez.tennis_scoreboard.service.OngoingMatchService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    @Inject
    private OngoingMatchService ongoingMatchService;
    @Inject
    private PlayerRepository playerRepository;
    @Inject
    private MatchScoreCalculationService matchScoreCalculationService;
    @Inject
    FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        Match match = ongoingMatchService.getOngoingMatch(uuid); //тут запрашивается по uuid матч когда uuid уже нет
        renderMatchScorePage(req, resp, uuid, match);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        int playerId = Integer.parseInt(req.getParameter("playerId"));
        Match match = ongoingMatchService.getOngoingMatch(uuid);
        matchScoreCalculationService.calculateScore(playerId, match);
        if (matchScoreCalculationService.isMatchEnded()){
            finishedMatchesPersistenceService.save(match);
            ongoingMatchService.removeOngoingMatch(uuid);
            matchScoreCalculationService.startMatch();
        }
        renderMatchScorePage(req, resp, uuid, match);
    }

    private void renderMatchScorePage(HttpServletRequest req, HttpServletResponse resp, UUID uuid, Match match) throws ServletException, IOException {
        req.setAttribute("match", match);
        req.setAttribute("uuid", uuid);
        req.setAttribute("playerOnePoints", matchScoreCalculationService.formatPoints(match.getMatchScore().getPlayerOneScore()));
        req.setAttribute("playerTwoPoints", matchScoreCalculationService.formatPoints(match.getMatchScore().getPlayerTwoScore()));
        req.setAttribute("matchOver", match.getWinner() != null);
        req.getRequestDispatcher("/match-score.jsp").forward(req, resp);
    }

}
