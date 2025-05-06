package com.skillnez.tennis_scoreboard.filter;

import com.skillnez.tennis_scoreboard.exception.SamePlayersNameException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;

import java.io.IOException;

@WebFilter("/*")
public class ErrorFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, resp);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (SamePlayersNameException | IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/new-match.jsp").forward(req, resp);
        }
    }
}
