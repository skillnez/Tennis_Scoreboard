package com.skillnez.tennis_scoreboard.controller;

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

@WebServlet("/new_match")
public class NewMatchServlet extends HttpServlet {

    @Inject
    private OngoingMatchService ongoingMatchService;
    @Inject
    private PlayerService playerService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerOne = req.getParameter("playerOne");
        String playerTwo = req.getParameter("playerTwo");
        playerService.takeOrSavePlayer(playerOne);
        playerService.takeOrSavePlayer(playerTwo);
        // TODO Не сохраняются игроки в базу, у обоих id = 0, надо доделать
        UUID uuid = ongoingMatchService.createOngoingMatch(playerOne, playerTwo);
        resp.sendRedirect("/match-score?uuid=" + uuid);
    }
}
