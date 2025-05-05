package com.skillnez.tennis_scoreboard.controller;

import com.skillnez.tennis_scoreboard.dao.MatchRepository;
import com.skillnez.tennis_scoreboard.entity.Match;
import com.skillnez.tennis_scoreboard.service.MatchPaginationService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class FinishedMatchesServlet extends HttpServlet {

    @Inject
    MatchPaginationService matchPaginationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNumber = Integer.parseInt(req.getParameter("page"));
        String playerName = req.getParameter("filter_by_player_name");
        req.setAttribute("startPage", matchPaginationService.getStartPage(pageNumber));
        req.setAttribute("endPage", matchPaginationService.getEndPage(pageNumber, playerName));
        req.setAttribute("totalPagesCount", matchPaginationService.getTotalPages(playerName));
        req.setAttribute("pageNumber", pageNumber);
        req.setAttribute("pagedMatches", matchPaginationService.getPagedMatches(pageNumber, playerName));

        req.getRequestDispatcher("/matches.jsp").forward(req, resp);
    }
}
