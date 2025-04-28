package com.skillnez.tennis_scoreboard.controller;

import com.skillnez.tennis_scoreboard.dao.PlayerRepository;
import com.skillnez.tennis_scoreboard.entity.Match;
import com.skillnez.tennis_scoreboard.entity.Player;
import com.skillnez.tennis_scoreboard.entity.PlayerScore;
import com.skillnez.tennis_scoreboard.service.OngoingMatchService;
import com.skillnez.tennis_scoreboard.service.PlayerService;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        Match match = ongoingMatchService.getOngoingMatch(uuid);
        match.getMatchScore().getPlayerOneScore().getPlayer().getName();
        req.setAttribute("match", match);
        req.setAttribute("uuid", uuid);
        req.getRequestDispatcher("/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        int playerId = Integer.parseInt(req.getParameter("playerId"));
        Match match = ongoingMatchService.getOngoingMatch(uuid);
        Player player = playerRepository.findById(playerId).get();
        if (player.getId().equals(match.getPlayer1().getId())) {
            match.getMatchScore().getPlayerOneScore().addGames();
            match.getMatchScore().getPlayerOneScore().addSets();
            match.getMatchScore().getPlayerOneScore().addPoints();
        } else {
            match.getMatchScore().getPlayerTwoScore().addGames();
            match.getMatchScore().getPlayerTwoScore().addSets();
            match.getMatchScore().getPlayerTwoScore().addPoints();
        }

        resp.sendRedirect("/match-score?uuid=" + uuid);

    }
}
