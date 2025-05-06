package com.skillnez.tennis_scoreboard.controller;

import com.skillnez.tennis_scoreboard.service.OngoingMatchService;
import com.skillnez.tennis_scoreboard.service.PlayerPersistenceService;
import com.skillnez.tennis_scoreboard.utils.Validator;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    @Inject
    private OngoingMatchService ongoingMatchService;
    @Inject
    private PlayerPersistenceService playerPersistenceService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerOne = req.getParameter("playerOne").trim();
        String playerTwo = req.getParameter("playerTwo").trim();
        Validator.validate(playerOne, playerTwo);
        UUID uuid = ongoingMatchService.createOngoingMatch(playerOne, playerTwo);
        resp.sendRedirect("/match-score?uuid=" + uuid);
    }
}
