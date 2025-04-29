package com.skillnez.tennis_scoreboard.controller;

import com.skillnez.tennis_scoreboard.dao.PlayerRepository;
import com.skillnez.tennis_scoreboard.entity.Match;
import com.skillnez.tennis_scoreboard.entity.Player;
import com.skillnez.tennis_scoreboard.entity.PlayerScore;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        Match match = ongoingMatchService.getOngoingMatch(uuid);
        req.setAttribute("match", match);
        req.setAttribute("uuid", uuid);
        req.getRequestDispatcher("/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        int playerId = Integer.parseInt(req.getParameter("playerId"));
        Match match = ongoingMatchService.getOngoingMatch(uuid);
        matchScoreCalculationService.calculateScore(playerId, match);
        resp.sendRedirect("/match-score?uuid=" + uuid);

    }

}
