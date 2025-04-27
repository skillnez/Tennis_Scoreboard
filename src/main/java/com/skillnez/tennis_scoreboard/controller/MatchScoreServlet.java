package com.skillnez.tennis_scoreboard.controller;

import com.skillnez.tennis_scoreboard.entity.MatchScore;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        //Временно, переделай на обработку optional
        MatchScore matchScore = ongoingMatchService.getOngoingMatch(uuid).get();
        int i = 0;
        req.setAttribute("matchScore", matchScore);
        req.setAttribute("uuid", uuid);
        req.getRequestDispatcher("match-score.jsp").forward(req, resp);
    }
}
